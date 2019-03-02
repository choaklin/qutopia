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
     * ID路径
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
     * 排序序号
     */
    private int sortNo;


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
