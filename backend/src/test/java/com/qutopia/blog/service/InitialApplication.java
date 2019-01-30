package com.qutopia.blog.service;

import com.qutopia.blog.entity.*;
import com.qutopia.blog.repository.CategoryRepository;
import com.qutopia.blog.repository.TagRepository;
import com.qutopia.blog.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

/**
 * 负责初始化数据的单元测试
 *
 * @author choaklin
 * @date 2018.12.05
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class InitialApplication {

    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private UserRepository userRepository;

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

        Map<Category, List<String>> categories = new LinkedHashMap<>();
        categories.put(
                new Category("Java SE", "java"),
                Arrays.asList(
                        "基础知识",
                        "高级特性",
                        "核心技术",
                        "JVM"
                )
        );
        categories.put(
                new Category("Java EE", "java"),
                Arrays.asList(
                        "Web应用技术",
                        "企业应用技术",
                        "Web服务技术",
                        "管理和安全性技术"
                )
        );
        categories.put(
                new Category("企业应用组件", "qiye"),
                Arrays.asList(
                        "依赖注入",
                        "认证授权",
                        "Web请求处理",
                        "Web内容处理",
                        "数据访问",
                        "作业调度",
                        "Web Service"
                )
        );
        categories.put(
                new Category("分布式应用组件", "fenbushijisuan"),
                Arrays.asList(
                        "分布式锁",
                        "消息队列",
                        "分布式作业调度"
                )
        );
        categories.put(
                new Category("设计之禅", "dao"),
                Arrays.asList(
                        "设计模式",
                        "架构模式",
                        "源码解读"
                ));
        categories.put(
                new Category("DevOps", "devops"),
                Arrays.asList(
                        "开发工具",
                        "持续构建",
                        "持续集成",
                        "软件测试",
                        "部署环境"
                )
        );
        categories.put(
                new Category("服务器", "fuwuqi"),
                Arrays.asList(
                        "Web服务器",
                        "Web（Servlet）容器",
                        "Web应用容器"
                )
        );
        categories.put(
                new Category("数据库", "database"),
                Arrays.asList(
                        "关系型",
                        "NoSQL"
                )
        );

        List<CategoryDO> subNodes = new ArrayList<>(30);

        int parentIndex = 1;
        for (Category category : categories.keySet()) {
            String parentName = category.getName();
            String icon = category.getIcon();

            CategoryDO parent = categoryRepository.findUniqueOneByQuery(Query.query(
                    Criteria.where("name").is(parentName)
            ));
            String parentId;
            if (parent != null) {
                parentId = parent.getId();
            } else {
                CategoryDO categoryDO = new CategoryDO();
                categoryDO.setName(parentName);
                categoryDO.setIcon(icon);
                categoryDO.setSortNo(parentIndex++);
                categoryRepository.create(categoryDO);
                parentId = categoryDO.getId();
            }

            List<String> subNodeNames = categories.get(category);
            int subIndex = 1;
            for (String subNodeName : subNodeNames) {
                CategoryDO categoryDO = new CategoryDO();
                categoryDO.setName(subNodeName);
                categoryDO.setParentId(parentId);
                categoryDO.setSortNo(subIndex++);
                subNodes.add(categoryDO);
            }
        }
        categoryRepository.create(subNodes);
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    class Category {
        private String name;
        private String icon;
    }

    /**
     * 初始化内置的管理员
     */
    @Test
    public void initAdministrator() {

        UserDO admin = new UserDO();
        admin.setNickname("choaklin");
        // 设置七牛云图片
        admin.setFigureUrl("http://resource.qutopia.cn/Fr1FPnf7pdyewxYr0ze0vM1EG65V");
        admin.setLoginInput("admin");
        admin.setPassword("123456");
        admin.setUserType(UserType.ADMIN.getValue());
        admin.setRegisterType(RegisterType.INITIALIZE.getValue());
        admin.setCreateTime(new Date());
        userRepository.create(admin);
    }

}
