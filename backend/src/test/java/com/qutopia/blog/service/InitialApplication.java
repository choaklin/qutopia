package com.qutopia.blog.service;

import com.qutopia.blog.entity.CategoryDO;
import com.qutopia.blog.repository.CategoryRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 负责初始化数据的单元测试
 *
 * @author choaklin
 * @date 2018.12.05
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class InitialApplication {

    @Autowired
    private CategoryRepository categoryRepository;


    @Test
    public void initCategories() {

        CategoryDO category = new CategoryDO();
        category.setName("JavaSE");
        categoryRepository.create(category);
    }

}
