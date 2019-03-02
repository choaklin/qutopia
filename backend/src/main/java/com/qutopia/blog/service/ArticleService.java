package com.qutopia.blog.service;

import com.qutopia.blog.cache.CacheUtil;
import com.qutopia.blog.entity.ArticleDO;
import com.qutopia.blog.entity.CategoryDO;
import com.qutopia.blog.entity.TagDimension;
import com.qutopia.blog.repository.ArticleRepository;
import com.qutopia.blog.repository.CategoryRepository;
import com.qutopia.blog.service.domain.DomainMapper;
import com.qutopia.blog.service.domain.article.*;
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
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

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
    private CategoryRepository categoryRepository;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private TagService tagService;



    /**
     * 新增一条文章的记录
     *
     * @param form
     */
    public void create(ArticleCreateAO form) {

        ArticleDO article = domainMapper.fromCreateAO(form);
        if (StringUtils.isNotBlank(form.getCategoryId())) {
            CategoryDO categoryDO = categoryRepository.findById(form.getCategoryId());
            Objects.nonNull(categoryDO);
            article.setCategoryPath(categoryDO.getPath());
        }

        List<String> tags;
        HashMap<String, String> tagMap = form.getTagMap();
        if (tagMap != null) {
            tags = new ArrayList<>(tagMap.size());

            tagMap.forEach((tagName, tagId) -> {
                if (StringUtils.isBlank(tagId)) {
                    tags.add(
                            tagService.create(TagDimension.TECHNIQUE, tagName)
                    );
                } else {
                    tags.add(tagId);
                }
            });
        } else {
            tags = Collections.emptyList();
        }
        article.setTags(tags);

        article.setCreateTime(LocalDateTime.now());
        articleRepository.create(article);
    }

    private List<String> tags(HashMap<String, String> tagMap) {
        if (tagMap != null) {
            List<String> tagIds = new ArrayList<>(tagMap.size());

            tagMap.forEach((tagName, tagId) -> {
                if (StringUtils.isBlank(tagId)) {
                    tagIds.add(
                            tagService.create(TagDimension.TECHNIQUE, tagName)
                    );
                } else {
                    tagIds.add(tagId);
                }
            });
        }

        return Collections.emptyList();
    }

    public void update(String id, ArticleUpdateAO form) {

        ArticleDO target = articleRepository.findById(id);
        Objects.requireNonNull(target, "未找到指定ID的文章: " + id);

        String oldCategoryId = target.getCategoryId();
        String newCategoryId = form.getCategoryId();
        String categoryPath = null;
        if (!oldCategoryId.equals(newCategoryId)) {
            // 更新新旧文章分类的文章数
            categoryService.rollArticleCount(oldCategoryId, false);

            CategoryDO newCategory = categoryService.rollArticleCount(newCategoryId, true);
            categoryPath = newCategory.getPath();
        }

        domainMapper.updateArticle(target, form);
        if (categoryPath != null) {
            target.setCategoryPath(categoryPath);
        }
        List<String> tagIds = this.tags(form.getTagMap());
        target.setTags(tagIds);
        target.setLastUpdateTime(LocalDateTime.now());
        articleRepository.update(target);
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

        Criteria criteria = Criteria.where("published").is(pageQuery.isPublished());
        if (StringUtils.isNotBlank(pageQuery.getCategoryId())) {
            criteria.and("categoryId").is(pageQuery.getCategoryId());
        }
        if (StringUtils.isNotBlank(pageQuery.getTitle())) {
            criteria.and("title").regex("^" + pageQuery.getTitle(), "i");
        }

        Page<ArticleDO> sourcePage = articleRepository.page(pageable, Query.query(criteria));
        if (sourcePage.getNumberOfElements() > 0) {

            List<ArticleDO> sources = sourcePage.getContent();

            List<ArticlePool> dests = new ArrayList<>(sources.size());
            for (ArticleDO source : sources) {

                ArticlePool dest = domainMapper.toPoolArticle(source);

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
