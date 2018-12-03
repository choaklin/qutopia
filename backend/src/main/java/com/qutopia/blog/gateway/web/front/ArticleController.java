package com.qutopia.blog.gateway.web.front;

import com.qutopia.blog.gateway.TemplateVariable;
import com.qutopia.blog.service.ArticleService;
import com.qutopia.blog.service.domain.article.ArticlePool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;
import java.util.List;

/**
 * 文章相关的处理
 *
 * @author choaklin
 * @date 2018.11.29
 */
@Controller
@RequestMapping("article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;


    /**
     * 默认先展示文章列表页
     * @return
     */
    @GetMapping
    public String list(@PageableDefault(page = 1, size = 5) Pageable pageable, Model model) {


        List<ArticlePool> articles = Arrays.asList();

        model.addAttribute(TemplateVariable.ARTICLES, articles);
        model.addAttribute(TemplateVariable.PAGE_NO, pageable.getPageNumber());
        model.addAttribute(TemplateVariable.PAGE_SIZE, pageable.getPageSize());
        return "article-list";
    }


    public Page<ArticlePool> next(@PageableDefault Pageable pageable, String title) {

        return articleService.page(pageable, title);
    }


    @GetMapping("/{id}")
    public String detail(@PathVariable String id) {

        return "article-detail";
    }
}
