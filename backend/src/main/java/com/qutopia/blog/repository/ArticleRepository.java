package com.qutopia.blog.repository;

import com.qutopia.blog.entity.ArticleDO;
import com.qutopia.blog.utils.data.mongo.AbstractMongoRepository;
import org.springframework.stereotype.Repository;

/**
 * 文章的数据访问对象
 */
@Repository
public class ArticleRepository extends AbstractMongoRepository<ArticleDO, String> {

}
