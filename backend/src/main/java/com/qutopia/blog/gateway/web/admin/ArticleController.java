package com.qutopia.blog.gateway.web.admin;

import com.qutopia.blog.service.ArticleService;
import com.qutopia.blog.service.domain.article.Article;
import com.qutopia.blog.service.domain.article.ArticleFormAO;
import com.qutopia.blog.service.domain.article.ArticlePageQuery;
import com.qutopia.blog.service.domain.article.ArticleUpdateAO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

/**
 * 文章的控制器
 *
 * @author choaklin
 * @date 2018/7/30
 * @version 0.0.1
 */
@Slf4j
@RestController
@RequestMapping("article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;


    @GetMapping
    @ResponseBody
    public Page<Article> index(@PageableDefault Pageable page, @ModelAttribute ArticlePageQuery pageQuery) {

        log.info(">> 进入index方法");
        return articleService.page(page, pageQuery);
    }


    @PostMapping
    public void create(@RequestBody ArticleFormAO form) {

    }

    @PutMapping(value = "/{id}")
    public void update(@PathVariable String id, @RequestBody ArticleUpdateAO update) {

    }

    @DeleteMapping("/{id}")
    public void destroy(@PathVariable String id) {

    }

    @GetMapping("/{id}")
    public Article show(@PathVariable String id) {

        return articleService.show(id);
    }

}