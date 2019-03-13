package com.qutopia.blog.gateway.web.front;

import com.qutopia.blog.cache.CacheUtil;
import com.qutopia.blog.entity.TagDimension;
import com.qutopia.blog.gateway.TemplateVariable;
import com.qutopia.blog.service.ArticleService;
import com.qutopia.blog.service.domain.article.Article;
import com.qutopia.blog.service.domain.article.ArticlePageQuery;
import com.qutopia.blog.service.domain.article.ArticlePool;
import com.qutopia.blog.service.domain.category.Category;
import com.qutopia.blog.service.domain.tag.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    private CacheUtil cacheUtil;

    @Autowired
    private ArticleService articleService;


    /**
     * 默认先展示文章列表页
     * @return
     */
    @GetMapping
    public String index(@PageableDefault(size = 2, sort = "createTime", direction = Sort.Direction.DESC) Pageable pageable, Model model) {

        //== 文章列表、文章分类、标签
        Page<ArticlePool> articles = articleService.page(pageable, ArticlePageQuery.builder().published(true).build());
        List<Category> categories = cacheUtil.getFrontCategories();
        List<Tag> tags = cacheUtil.getFrontTag(TagDimension.TECHNIQUE);

        model.addAttribute(TemplateVariable.ARTICLES, articles.getContent());
        model.addAttribute(TemplateVariable.CATEGORIES, categories);
        model.addAttribute(TemplateVariable.TAGS, tags);

        model.addAttribute(TemplateVariable.PAGE_NUMBER, articles.getNumber());
        model.addAttribute(TemplateVariable.PAGE_SIZE, articles.getSize());
        model.addAttribute(TemplateVariable.TOTAL_PAGES, articles.getTotalPages());
        return "article-list";
    }

    /**
     * 下一页
     *
     * @param pageable
     * @param title
     * @param categoryId
     * @param tagIds get方式传递，url格式是 http:domain:port?tagIds=id1,id2,id3
     * @return
     */
    @ResponseBody
    @GetMapping(value = "page")
    public Page<ArticlePool> page(@PageableDefault Pageable pageable, String title, String categoryId, @RequestParam("tagIds") List<String> tagIds) {

        return articleService.page(
                pageable,
                ArticlePageQuery.builder()
                        .published(true)
                        .title(title)
                        .categoryId(categoryId)
                        .tagIds(tagIds)
                        .build()
        );
    }


    @GetMapping("{id}")
    public String detail(@PathVariable String id, Model model) {

        Article article = articleService.show(id);
        model.addAttribute(TemplateVariable.ARTICLE, article);

        return "article-detail";
    }
}
