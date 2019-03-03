package com.qutopia.blog.gateway.web.admin;

import com.qutopia.blog.cache.CacheUtil;
import com.qutopia.blog.service.CategoryService;
import com.qutopia.blog.service.domain.category.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author choaklin
 * @date 2018.12.15
 */
@RestController
@RequestMapping("categoryManage")
public class CategoryManageController {

    @Autowired
    private CacheUtil cacheUtil;
    @Autowired
    private CategoryService categoryService;


    @GetMapping("list")
    public List<Category> list(@RequestParam("parentId") String parentId) {

        return categoryService.listByParentId(parentId);
    }

}
