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


    public void rollArticleCount(boolean up, boolean published) {

        if (up) {
            referenceCount += 1;
            if (published) {
                publishedCount += 1;
            }
        } else {
            if (referenceCount > 0) {
                referenceCount -= 1;
            }
            if (published) {
                if (publishedCount > 0) {
                    publishedCount -= 1;
                }
            }
        }
    }

}
