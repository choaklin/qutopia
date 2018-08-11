package com.qutopia.blog.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document(collection = CollectionNames.ARTICLE)
public class ArticleDO {

    /**
     * 文章ID
     */
    @Id
    private String id;

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
    private String tags;

    /**
     * 是否发布, 未发布则是草稿状态
     */
    private boolean published;

    /**
     * 是否允许评论
     */
    private boolean commentable;

    /**
     * 永久链接
     */
    private String permalink;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 最后更新时间
     */
    private LocalDateTime lastUpdateTime;
}
