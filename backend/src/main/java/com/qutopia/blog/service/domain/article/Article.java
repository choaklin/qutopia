package com.qutopia.blog.service.domain.article;

import com.qutopia.blog.entity.CreateType;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @see com.qutopia.blog.entity.ArticleDO
 */
@Data
public class Article {

    /**
     * 文章ID
     */
    private String id;

    /**
     * 分类ID
     */
    private String categoryId;

    /**
     * 分类路径
     */
    private String categoryPath;

    /**
     * 标题
     */
    private String title;

    /**
     * 文章的背景小图片链接
     */
    private String thumbnail;

    /**
     * 概述
     */
    private String overview;

    /**
     * 正文
     */
    private String content;

    /**
     * 标签
     */
    private List<ArticleTag> articleTags;

    /**
     * 创建类型
     */
    private CreateType createType;

    /**
     * 转载的原地址
     */
    private String reproduceUrl;

    /**
     * 是否发布, 未发布则是草稿状态
     */
    private boolean published;

    /**
     * 是否允许评论
     */
    private boolean commentable;

    /**
     * 浏览数
     */
    private int viewCount;

    /**
     * 评论数
     */
    private int commentCount;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 最后更新时间
     */
    private LocalDateTime lastUpdateTime;

}
