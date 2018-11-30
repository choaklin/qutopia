package com.qutopia.blog.gateway.web.front;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author choaklin
 * @date 2018.11.29
 */
@Controller
@RequestMapping("article")
public class ArticleController {


    @GetMapping
    public String list() {

        return "article-list";
    }


    @GetMapping("/{id}")
    public String detail(@PathVariable String id) {

        return "article-detail";
    }
}
