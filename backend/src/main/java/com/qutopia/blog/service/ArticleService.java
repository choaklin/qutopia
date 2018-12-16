package com.qutopia.blog.service;

import com.qutopia.blog.entity.ArticleDO;
import com.qutopia.blog.entity.TagDimension;
import com.qutopia.blog.repository.ArticleRepository;
import com.qutopia.blog.service.domain.DomainMapper;
import com.qutopia.blog.service.domain.article.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
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

    @Autowired
    private DomainMapper domainMapper;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private TagService tagService;

    /**
     * 新增一条文章的记录
     *
     * @param create
     */
    public void create(ArticleCreateAO create) {

        ArticleDO article = domainMapper.fromCreateAO(create);
        List<String> tags;
        HashMap<String, String> tagMap = create.getTagMap();
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

    public void update() {

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
        Page<ArticleDO> sourcePage = articleRepository.page(pageable, Query.query(criteria));
        if (sourcePage.getNumberOfElements() > 0) {

            List<ArticleDO> sources = sourcePage.getContent();
            List<String> tagIds;
            Map<String, ArticleTag> tagCache = new HashMap<>();

            List<ArticlePool> dests = new ArrayList<>(sources.size());
            for (ArticleDO source : sources) {

                ArticlePool dest = domainMapper.toPoolArticle(source);
                tagIds = source.getTags();
                if (CollectionUtils.isNotEmpty(tagIds)) {
                    List<ArticleTag> tags = new ArrayList<>(tagIds.size());

                    for (String tagId: tagIds) {
                        ArticleTag tag = tagCache.get(tagId);
                        if (tag == null) {

                            tag = ArticleTag.buildFriendly(
                                    tagService.get(tagId)
                            );
                            tagCache.put(tagId, tag);
                        }
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
        }
        return dest;
    }
}
