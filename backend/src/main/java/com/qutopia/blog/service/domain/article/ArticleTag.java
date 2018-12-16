package com.qutopia.blog.service.domain.article;

import com.qutopia.blog.service.domain.tag.Tag;
import lombok.Data;

/**
 * 文章标签
 *
 * @author choaklin
 * @date 2018.12.16
 */
@Data
public class ArticleTag {

    /**
     * 文章的标签ID
     */
    private String tagId;

    /**
     * 标签名称
     */
    private String name;

    /**
     * 标签的描述
     */
    private String desc;


    public static ArticleTag buildFriendly(Tag tag) {
        ArticleTag dest = new ArticleTag();

        if (tag != null) {
            dest.setTagId(tag.getId());
            dest.setName(tag.getName());
            dest.setDesc(tag.getDesc());
        } else {
            // -1表示该标签是非法数据：真实的数据已丢失、数据库数据被破坏
            dest.setTagId("-1");
            dest.setName("-");
            dest.setDesc("-");
        }
        return dest;
    }

}
