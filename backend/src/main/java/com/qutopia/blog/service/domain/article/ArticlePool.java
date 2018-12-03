package com.qutopia.blog.service.domain.article;

import java.util.Date;

/**
 * @author choaklin
 * @date 2018.12.01
 */
public class ArticlePool {

    /**
     * 文章ID
     */
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
    private Date createTime;

}
