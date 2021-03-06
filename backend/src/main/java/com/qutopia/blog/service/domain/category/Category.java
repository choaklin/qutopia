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
     * 分类路径
     */
    private String path;

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
     * 排序编号
     */
    private int sortNo;

    /**
     * 子分类
     */
    private List<Category> children;


    public void rollArticleCount(boolean up) {

        if (up) {
            articleCount += 1;
        } else {
            if (articleCount > 0) {
                articleCount -= 1;
            }
        }
    }
}
