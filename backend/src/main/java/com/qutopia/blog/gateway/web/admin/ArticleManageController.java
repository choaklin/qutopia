package com.qutopia.blog.gateway.web.admin;

import com.qutopia.blog.service.ArticleService;
import com.qutopia.blog.service.domain.article.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

/**
 * 文章的控制器
 *
 * @author choaklin
 * @date 2018/7/30
 * @version 0.0.1
 */
@Slf4j
@RestController
@RequestMapping("articleManage")
public class ArticleManageController {

    @Autowired
    private ArticleService articleService;


    @GetMapping
    public Page<ArticlePool> index(@PageableDefault(sort = "createTime", direction = Sort.Direction.DESC) Pageable page, @ModelAttribute ArticlePageQuery pageQuery) {

        log.info(">> 进入index方法");
        return articleService.page(page, pageQuery);
    }

    @PostMapping
    public void create(@RequestBody @Valid ArticleCreateAO create) {

        log.info(">> increase new article [{}]", create);
        articleService.create(create);
    }

    @PutMapping(value = "{id}")
    public void update(@PathVariable @NotBlank(message = "指定修改的文章ID不能空白") String id, @RequestBody ArticleUpdateAO update) {

        log.info(">> update [{}] with [{}]", update);
        articleService.update(id, update);
    }

    @PatchMapping("{id}/delete")
    public void delete(@PathVariable String id) {

        articleService.toggleAvailable(id, false);
    }

    @PatchMapping("{id}/recover")
    public void recover(@PathVariable String id) {

        articleService.toggleAvailable(id, true);
    }

    @DeleteMapping("{id}")
    public void destroy(@PathVariable String id) {

        log.info(">> delete [{}] article", id);
        articleService.destroy(id);
    }

    @GetMapping("{id}")
    public Article get(@PathVariable String id) {

        return articleService.get(id);
    }

}
