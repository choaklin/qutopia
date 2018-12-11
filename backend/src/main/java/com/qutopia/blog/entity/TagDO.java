package com.qutopia.blog.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

/**
 * 标签的数据对象
 *
 * @author choaklin
 * @version 0.0.1
 * @date 2018/9/1
 */
@Data
@Document(collection = CollectionNames.TAG)
public class TagDO implements Serializable {

    /**
     * 标签ID
     */
    @Id
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
     * 标签的图标地址
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
