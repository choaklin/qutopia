package com.qutopia.blog.service.domain;

import com.qutopia.blog.entity.ArticleDO;
import com.qutopia.blog.entity.TagDO;
import com.qutopia.blog.service.domain.article.Article;
import com.qutopia.blog.service.domain.article.ArticleCreateAO;
import com.qutopia.blog.service.domain.article.ArticleUpdateAO;
import com.qutopia.blog.service.domain.tag.Tag;
import org.mapstruct.Mapper;

/**
 * @author choaklin
 * @version 0.0.1
 * @date 2018/8/11
 */
@Mapper(componentModel = "spring")
public interface DomainMapper {

    ArticleDO fromCreateAO(ArticleCreateAO create);

    ArticleDO fromUpdateAO(ArticleUpdateAO update);

    Article toArticle(ArticleDO source);

    Tag toTag(TagDO tag);
}
