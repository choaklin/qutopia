package com.qutopia.blog.service.domain.article;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * <p>文章的“廋”对象, 不输出文章内容（大文本）</p>
 * <p>适合于获取文章列表的应用场景使用</p>
 *
 * @author choaklin
 * @date 2018.12.01
 */
@Data
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
     * 文章的标签
     */
    private List<ArticleTag> articleTags;

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
