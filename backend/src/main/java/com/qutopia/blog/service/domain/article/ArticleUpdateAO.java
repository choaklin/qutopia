package com.qutopia.blog.service.domain.article;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.HashMap;

/**
 * @author choaklin
 * @version 0.0.1
 */
@Data
public class ArticleUpdateAO {

    /**
     * 标题
     */
    @NotBlank(message = "文章的标题不能是空白的")
    private String title;

    /**
     * 文章的背景小图片链接
     */
    private String thumbnail;

    /**
     * 文章分类ID
     */
    @NotBlank(message = "文章的系统分类不能是空白的")
    private String categoryId;

    /**
     * 概述
     */
    private String overview;

    /**
     * 正文
     */
    @NotBlank(message = "文章正文不能是空白的")
    private String content;

    /**
     * <p>文章所属的标签集合</p>
     * <p>key: 标签名称, value: 标签ID(系统已存在时)或空字符串, 非Null</p>
     * <p>空字符串的标签要进行新增操作</p>
     */
    @NotEmpty(message = "")
    private HashMap<String, String> tagMap;

    /**
     * 是否允许评论
     */
    private boolean commentable;

    /**
     * 是否发布, 未发布则是草稿状态
     */
    private boolean published;
}
