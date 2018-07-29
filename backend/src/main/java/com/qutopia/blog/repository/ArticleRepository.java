package com.qutopia.blog.repository;

import com.qutopia.blog.entity.Article;
import com.qutopia.blog.utils.mongo.AbstractMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public class ArticleRepository extends AbstractMongoRepository<Article, String> {

}
