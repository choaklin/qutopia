package com.qutopia.blog.service;


import com.qutopia.blog.entity.CategoryDO;
import com.qutopia.blog.repository.CategoryRepository;
import com.qutopia.blog.service.domain.DomainMapper;
import com.qutopia.blog.service.domain.category.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 分类的服务
 *
 * @author choaklin
 * @date 2018/12/12
 */
@Service
public class CategoryService {

    public static final String ROOT_NODE = "0";

    @Autowired
    private DomainMapper domainMapper;
    @Autowired
    private CategoryRepository categoryRepository;


    public List<Category> listByParentId(String parentId) {

        List<CategoryDO> sources = categoryRepository.list(
                Query.query(Criteria.where("parentId").is(parentId))
        );

        List<Category> result = domainMapper.toCategoryList(sources);
        result.forEach(category -> {

            List<CategoryDO> children = categoryRepository.list(Query.query(
                    // 二级分类搜索有文章的
                    Criteria.where("parentId").is(category.getId())
                            // .and("articleCount").gt(0)
            ));
            category.setChildren(domainMapper.toCategoryList(children));
        });
        return result;
    }
}
