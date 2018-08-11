package com.qutopia.blog.service;

import com.qutopia.blog.entity.ArticleDO;
import com.qutopia.blog.repository.ArticleRepository;
import com.qutopia.blog.service.domain.DomainMapper;
import com.qutopia.blog.service.domain.article.Article;
import com.qutopia.blog.service.domain.article.ArticlePageQuery;
import com.qutopia.blog.utils.data.commons.UUIDGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.Arrays;

/**
 * 文章的服务
 *
 * @author choaklin
 * @version 0.0.1
 * @date 2018/7/30
 */
@Slf4j
@Service
public class ArticleService {

    @Autowired
    private DomainMapper domainMapper;

    @Autowired
    private ArticleRepository articleRepository;


    public void create() {

    }

    public void update() {

    }


    public Page<Article> page(Pageable pageable, ArticlePageQuery pageQuery) {

        Page result = articleRepository.page(pageable, new Query());
        if (result.getTotalElements() < 1) {
            ArticleDO temp = articleRepository.findById("1111");

            ArticleDO article = new ArticleDO();
            article.setId(UUIDGenerator.generate());
            article.setTitle("世界，你好！");

            result = new PageImpl(Arrays.asList(domainMapper.toArticle(article)), pageable, 1);
        }
        return result;
    }

    public Article show(String id) {
        Article dest = null;

        ArticleDO source = articleRepository.findById(id);
        if (source != null) {
            dest = domainMapper.toArticle(source);
        }
        return dest;
    }
}
