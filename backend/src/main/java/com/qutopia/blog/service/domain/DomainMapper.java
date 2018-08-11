package com.qutopia.blog.service.domain;

import com.qutopia.blog.entity.ArticleDO;
import com.qutopia.blog.service.domain.article.Article;
import org.mapstruct.Mapper;

/**
 *
 *
 * @author choaklin
 * @version 0.0.1
 * @date 2018/8/11
 */
@Mapper(componentModel = "spring")
public interface DomainMapper {

    Article toArticle(ArticleDO source);
}
