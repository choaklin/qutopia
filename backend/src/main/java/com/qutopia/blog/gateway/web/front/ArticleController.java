package com.qutopia.blog.gateway.web.front;

import com.qutopia.blog.gateway.TemplateVariable;
import com.qutopia.blog.service.ArticleService;
import com.qutopia.blog.service.CategoryService;
import com.qutopia.blog.service.TagService;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private TagService tagService;


    /**
     * 默认先展示文章列表页
     * @return
     */
    @GetMapping
    public String index(@PageableDefault(size = 5, sort = "createTime", direction = Sort.Direction.DESC) Pageable pageable, Model model) {

        //== 文章列表、文章分类、标签
        Page<ArticlePool> articles = articleService.page(pageable, ArticlePageQuery.builder().published(true).build());
        List<Category> categories = categoryService.listByParentId(CategoryService.ROOT_NODE);
        List<Tag> tags = tagService.list(null);

        model.addAttribute(TemplateVariable.ARTICLES, articles.getContent());
        model.addAttribute(TemplateVariable.CATEGORIES, categories);
        model.addAttribute(TemplateVariable.TAGS, tags);

        model.addAttribute(TemplateVariable.PAGE_NO, pageable.getPageNumber());
        model.addAttribute(TemplateVariable.PAGE_SIZE, pageable.getPageSize());
        return "article-list";
    }

    /**
     * 下一页
     *
     * @param pageable
     * @param title
     * @param categoryId
     * @param tagId
     * @return
     */
    @ResponseBody
    @GetMapping(value = "page")
    public Page<ArticlePool> page(@PageableDefault Pageable pageable, String title, String categoryId, String tagId) {

        return articleService.page(
                pageable,
                ArticlePageQuery.builder()
                        .published(true)
                        .title(title)
                        .categoryId(categoryId)
                        .tagId(tagId)
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
