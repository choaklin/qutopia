package com.qutopia.blog.service.domain.article;

import lombok.Builder;
import lombok.Data;

/**
 * @author choaklin
 * @version 0.0.1
 * @date 2018/8/11
 */
@Data
@Builder
public class ArticlePageQuery {

    /**
     * 文章标题
     */
    private String title;

    /**
     * 文章分类ID
     */
    private String categoryId;

    /**
     * 标签ID
     */
    private String tagId;

    /**
     * 是否已发布
     */
    private boolean published;

}
