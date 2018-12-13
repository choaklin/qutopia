package com.qutopia.blog.service;

import com.qutopia.blog.entity.CategoryDO;
import com.qutopia.blog.entity.TagDO;
import com.qutopia.blog.entity.TagDimension;
import com.qutopia.blog.repository.CategoryRepository;
import com.qutopia.blog.repository.TagRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    private TagRepository tagRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    public void initTags() {

        List<Tag> internalTags = Arrays.asList(
                new Tag("Maven", "", "Maven 是基于项目对象模型（POM）、通过一小段描述信息来管理项目的构建、报告和文档的软件项目管理工具。"),
                new Tag("IDEA", "", "IDEA 全称 IntelliJ IDEA，是一款 Java 语言开发的集成环境，在业界被公认为最好的 Java 开发工具之一。IDEA 是 JetBrains 公司的产品，这家公司总部位于捷克共和国的首都布拉格，开发人员以严谨著称的东欧程序员为主。"),
                new Tag("Spring", "", "Spring 是一个开源框架，是于 2003 年兴起的一个轻量级的 Java 开发框架，由 Rod Johnson 在其著作《Expert One-On-One J2EE Development and Design》中阐述的部分理念和原型衍生而来。它是为了解决企业应用开发的复杂性而创建的。框架的主要优势之一就是其分层架构，分层架构允许使用者选择使用哪一个组件，同时为 JavaEE 应用程序开发提供集成的框架。")
        );

        List<TagDO> tags = new ArrayList<>(internalTags.size());
        internalTags.forEach(p -> {

            TagDO tag = new TagDO();
            tag.setName(p.getName());
            tag.setIcon(p.getIcon());
            tag.setDesc(p.getDesc());
            tag.setDimension(TagDimension.TECHNIQUE);
            tag.setInternal(true);
            tags.add(tag);
        });
        tagRepository.create(tags);
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    class Tag {
        private String name;
        private String icon;
        private String desc;
    }

    @Test
    public void initCategories() {

        CategoryDO category = new CategoryDO();
        category.setName("JavaSE");
        categoryRepository.create(category);
    }

}
