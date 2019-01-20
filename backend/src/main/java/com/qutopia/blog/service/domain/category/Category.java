package com.qutopia.blog.service.domain.category;

import lombok.Data;

import java.util.List;

/**
 * @author choaklin
 * @date 2018.12.15
 */
@Data
public class Category {

    /**
     * 分类ID
     */
    private String id;

    /**
     * <p>上级分类ID</p>
     * <p>顶级分类的上级分类ID是0</p>
     */
    private String parentId;

    /**
     * 名称
     */
    private String name;

    /**
     * 图标
     */
    private String icon;

    /**
     * 文章数量
     */
    private int articleCount;

    /**
     * 子分类
     */
    private List<Category> children;
}
