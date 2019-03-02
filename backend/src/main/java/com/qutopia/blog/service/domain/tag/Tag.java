package com.qutopia.blog.service.domain.tag;

import com.qutopia.blog.entity.TagDimension;
import lombok.Data;

/**
 * 标签
 */
@Data
public class Tag {

    /**
     * 标签ID
     */
    private String id;

    /**
     * 标签的所属维度
     */
    private TagDimension dimension;

    /**
     * 名字
     */
    private String name;

    /**
     * <p>是否内置</p>
     * <p>文章列表页展示的标签是内置的，相比于自定义，内置的一般有图标和详细描述</p>
     */
    private boolean internal;

    /**
     * 标签的图标地址，一般是{@link #internal 内置}标签
     */
    private String icon;

    /**
     * 描述
     */
    private String desc;

    /**
     * 关联的文章数
     */
    private int referenceCount;

    /**
     * 关联已发布的文章数
     */
    private int publishedCount;
}
