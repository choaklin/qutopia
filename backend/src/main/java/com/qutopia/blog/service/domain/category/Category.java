package com.qutopia.blog.service.domain.category;

import lombok.Data;

/**
 * @author choaklin
 * @date 2018.12.15
 */
@Data
public class Category {

    private String id;

    /**
     * <p>上级分类ID</p>
     * <p>顶级分类的上级分类ID是0</p>
     */
    private String parentId = "0";

    /**
     * 名称
     */
    private String name;
}
