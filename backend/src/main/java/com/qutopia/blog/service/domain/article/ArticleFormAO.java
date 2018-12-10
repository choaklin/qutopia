package com.qutopia.blog.service.domain.article;

import lombok.Data;

import java.util.Map;

/**
 * @author choaklin
 * @version 0.0.1
 * @date 2018/8/11
 */
@Data
public class ArticleFormAO {

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
     * <p>文章所属的标签集合</p>
     * <p>key: 标签名称, value: 标签ID(系统已存在时)或空字符串, 非Null</p>
     * <p>空字符串的标签要进行新增操作</p>
     */
    private Map<String, String> tags;

    /**
     * 是否允许评论
     */
    private boolean commentable;

    /**
     * 是否发布, 未发布则是草稿状态
     */
    private boolean published;

}
