package com.qutopia.blog.entity;

import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

public class Article {

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
     * 文章的背景小图片
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
     * 永久链接
     */
    private String permalink;

    /**
     * 标签
     */
    private String tags;

    /**
     * 是否发布
     */
    private Boolean published;

    /**
     * 是否允许评论
     */
    private Boolean commentable;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 最后更新时间
     */
    private LocalDateTime lastUpdateTime;
}
