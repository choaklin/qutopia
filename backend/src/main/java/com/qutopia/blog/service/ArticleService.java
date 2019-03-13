package com.qutopia.blog.service;

import com.qutopia.blog.cache.CacheUtil;
import com.qutopia.blog.entity.ArticleDO;
import com.qutopia.blog.entity.CategoryDO;
import com.qutopia.blog.entity.TagDO;
import com.qutopia.blog.entity.TagDimension;
import com.qutopia.blog.repository.ArticleRepository;
import com.qutopia.blog.service.domain.DomainMapper;
import com.qutopia.blog.service.domain.article.*;
import com.qutopia.blog.service.domain.category.Category;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.*;

/**
 * 文章的服务
 *
 * @author choaklin
 * @version 0.0.1
 * @date 2018/7/30
 */
@Slf4j
@Service
public class ArticleService {

    private final Parser parser = Parser.builder().build();
    private final HtmlRenderer renderer = HtmlRenderer.builder().build();

    @Autowired
    private DomainMapper domainMapper;
    @Autowired
    private CacheUtil cacheUtil;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private TagService tagService;



    /**
     * 新增一条文章的记录
     *
     * @param form 文章新增的表单对象
     */
    public void create(ArticleCreateAO form) {

        ArticleDO article = domainMapper.fromCreateAO(form);
        article.setAvailable(true);
        CategoryDO categoryDO = categoryService.rollArticleCount(form.getCategoryId(), true);
        article.setCategoryPath(categoryDO.getPath());

        List<String> tagIds = this.referenceTags(form.getTagMap(), form.isPublished());
        article.setTags(tagIds);

        article.setCreateTime(LocalDateTime.now());
        articleRepository.create(article);
    }

    private List<String> referenceTags(Map<String, String> tagMap, boolean published) {
        if (tagMap != null) {
            List<String> tagIds = new ArrayList<>(tagMap.size());
            tagMap.forEach((tagName, tagId) -> {
                TagDO tagDO = tagService.rollMaterialCount(TagDimension.TECHNIQUE, tagId, tagName, true, published);
                tagIds.add(tagDO.getId());
            });
            return tagIds;
        }
        return Collections.emptyList();
    }

    /**
     * 更新文章
     *
     * @param id 文章ID
     * @param form 文章更新的表单对象
     */
    public void update(String id, ArticleUpdateAO form) {

        ArticleDO target = articleRepository.findById(id);
        Objects.requireNonNull(target, "未找到指定ID的文章: " + id);

        CategoryDO newCategory = categoryService.replaceCategory(target.getCategoryId(), form.getCategoryId());
        domainMapper.updateArticle(target, form);
        if (newCategory != null) {
            target.setCategoryPath(newCategory.getPath());
        }

        List<String> removeTagIds = target.getTags();
        Map<String, String> formTags = form.getTagMap();
        Map<String, String> increaseTags = new HashMap<>();
        List<String> keepIds = new ArrayList<>();

        // 系统不存在的Tag、新引用的Tag, 按新增方式处理
        // 已有的TagID、要删除的TagID
        formTags.forEach((name, tagId) -> {
            if (StringUtils.isBlank(tagId)) {
                increaseTags.put(name, tagId);
            } else {
                // 移除成功，表示已
                if (removeTagIds.remove(tagId)) {
                    keepIds.add(tagId);
                } else {
                    increaseTags.put(name, tagId);
                }
            }
        });

        // 处理标签的逻辑：现有的(已存在和新增)、删除的
        if (increaseTags.size() > 0) {
            List<String> tagIds = this.referenceTags(increaseTags, form.isPublished());
            keepIds.addAll(tagIds);
        }

        if (CollectionUtils.isNotEmpty(removeTagIds)) {
            removeTagIds.forEach(tagId -> {
                tagService.rollMaterialCount(TagDimension.TECHNIQUE, tagId, null,false, target.isPublished());
            });
        }

        target.setTags(keepIds);
        target.setLastUpdateTime(LocalDateTime.now());
        articleRepository.update(target);
    }


    public void toggleAvailable(String id, boolean available) {

        Query query = Query.query(Criteria.where("id").is(id));
        query.fields().exclude("content");
        ArticleDO article = articleRepository.findUniqueOneByQuery(query);

        Assert.isTrue(article.isAvailable() != available, "文章已是正常或回收状态");

        // 增加或减少 分类和标签的文章数量
        if (StringUtils.isNotBlank(article.getCategoryId())) {
            categoryService.rollArticleCount(article.getCategoryId(), available);
        }
        article.getTags().forEach(tagId -> tagService.rollMaterialCount(TagDimension.TECHNIQUE, tagId, null, available, article.isPublished()));

        articleRepository.updateOne(id, Update.update("available", available));
    }


    /**
     * 物理删除
     * @param id
     */
    public void destroy(String id) {

        Query query = Query.query(Criteria.where("id").is(id));
        query.fields().exclude("content");
        ArticleDO article = articleRepository.findUniqueOneByQuery(query);
        Objects.requireNonNull(article, "未找到指定的文章");

        // 增加或减少 分类和标签的文章数量
        categoryService.rollArticleCount(article.getCategoryId(), false);
        article.getTags().forEach(tagId -> tagService.rollMaterialCount(TagDimension.TECHNIQUE, tagId, null, false, article.isPublished()));

        articleRepository.deleteById(id);
    }


    /**
     * 分页获取文章的“廋”对象
     *
     * @param pageable
     * @param pageQuery
     * @return
     */
    public Page<ArticlePool> page(Pageable pageable, ArticlePageQuery pageQuery) {

        Page<ArticlePool> resultPage;

        Criteria criteria = Criteria.where("available").is(true);
        if (pageQuery.getPublished() != null) {
            criteria.and("published").is(pageQuery.getPublished());
        }
        if (StringUtils.isNotBlank(pageQuery.getCategoryId())) {
            criteria.and("categoryId").is(pageQuery.getCategoryId());
        }
        if (StringUtils.isNotBlank(pageQuery.getTitle())) {
            criteria.and("title").regex("^" + pageQuery.getTitle(), "i");
        }
        if (CollectionUtils.isNotEmpty(pageQuery.getTagIds())) {
            criteria.and("tags").in(pageQuery.getTagIds());
        }

        Page<ArticleDO> sourcePage = articleRepository.page(pageable, Query.query(criteria).with(new Sort(Sort.Direction.DESC, "createTime")));
        if (sourcePage.getNumberOfElements() > 0) {

            List<ArticleDO> sources = sourcePage.getContent();

            List<ArticlePool> dests = new ArrayList<>(sources.size());
            for (ArticleDO source : sources) {

                ArticlePool dest = domainMapper.toPoolArticle(source);

                Category category = cacheUtil.getCacheCategory(source.getCategoryId());
                if (category != null) {
                    dest.setCategory(category.getName());
                } else {
                    dest.setCategory("-");
                }

                List<String> tagIds = source.getTags();
                if (CollectionUtils.isNotEmpty(tagIds)) {
                    List<ArticleTag> tags = new ArrayList<>(tagIds.size());
                    for (String tagId: tagIds) {
                        ArticleTag tag = ArticleTag.buildFriendly(cacheUtil.getCacheTag(tagId));
                        tags.add(tag);
                    }
                    dest.setArticleTags(tags);
                }
                dests.add(dest);
            }
            resultPage = new PageImpl(dests, pageable, sourcePage.getTotalElements());
        } else {
            resultPage = new PageImpl<>(Collections.EMPTY_LIST, pageable, 0);
        }
        return resultPage;
    }


    /**
     * 获取文章的详情
     *
     * @param id
     * @return
     */
    public Article get(String id) {
        Article dest = null;

        ArticleDO source = articleRepository.findById(id);
        if (source != null) {
            dest = domainMapper.toArticle(source);

            List<String> tagIds = source.getTags();
            if (CollectionUtils.isNotEmpty(tagIds)) {
                List<ArticleTag> tags = new ArrayList<>(tagIds.size());
                for (String tagId: tagIds) {
                    ArticleTag tag = ArticleTag.buildFriendly(cacheUtil.getCacheTag(tagId));
                    tags.add(tag);
                }
                dest.setArticleTags(tags);
            }
        }
        return dest;
    }


    /**
     * 展示指定ID的文章
     *
     * @return
     */
    public Article show(String id) {
        Article dest = null;

        ArticleDO source = articleRepository.findById(id);
        if (source != null) {

            // 更新浏览数
            int viewCount = source.getViewCount() + 1;
            articleRepository.updateOne(id, Update.update("viewCount", viewCount));

            dest = domainMapper.toArticle(source);
            // 解析markdown
            if (StringUtils.isNotBlank(dest.getContent())) {
                Node node = parser.parse(dest.getContent());
                dest.setContent(renderer.render(node));
            }
        }
        return dest;
    }
}
