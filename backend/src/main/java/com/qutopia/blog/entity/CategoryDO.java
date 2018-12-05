package com.qutopia.blog.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

/**
 * 分类
 *
 * @author choaklin
 * @date 2018.12.05
 */
@Data
@Document(collection = CollectionNames.CATEGORY)
public class CategoryDO implements Serializable {

    @Id
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
