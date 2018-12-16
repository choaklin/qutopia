package com.qutopia.blog.cache;

import com.qutopia.blog.repository.CategoryRepository;
import com.qutopia.blog.repository.TagRepository;
import com.qutopia.blog.service.domain.category.Category;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 本地一级缓存设施
 *
 * @author choaklin
 * @date 2018.12.16
 */
@Slf4j
@Component
public class CacheUtil implements ApplicationRunner {

    private Map<String, Category> categorys = new HashMap<>();

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private TagRepository tagRepository;


    /**
     * springboot 启动完成去执行的业务逻辑
     *
     * @param args
     * @throws Exception
     */
    @Override
    public void run(ApplicationArguments args) throws Exception {

        initCategory();
    }

    protected void initCategory() {

        // categoryRepository.list()
        log.info(">> 完成构建[文章分类]的缓存");
    }
}
