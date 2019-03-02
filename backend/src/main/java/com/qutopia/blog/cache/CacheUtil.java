package com.qutopia.blog.cache;

import com.qutopia.blog.entity.CategoryDO;
import com.qutopia.blog.entity.TagDO;
import com.qutopia.blog.entity.TagDimension;
import com.qutopia.blog.repository.CategoryRepository;
import com.qutopia.blog.repository.TagRepository;
import com.qutopia.blog.service.CategoryService;
import com.qutopia.blog.service.domain.DomainMapper;
import com.qutopia.blog.service.domain.category.Category;
import com.qutopia.blog.service.domain.tag.Tag;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 本地一级缓存设施
 *
 * @author choaklin
 * @date 2018.12.16
 */
@Slf4j
@Component
public class CacheUtil implements ApplicationRunner {

    private Map<String, Category> categories = new HashMap<>();
    private Map<String, Tag> tags = new HashMap<>();

    @Autowired
    private DomainMapper domainMapper;
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

        cacheCategory();
        cacheTag();
    }

    protected void cacheCategory() {


        List<CategoryDO> sources = categoryRepository.list(Query.query(Criteria.where("name").exists(true)));
        if (sources.size() > 0) {
            categories = sources.stream().collect(Collectors.toMap(CategoryDO::getId, p -> domainMapper.toCategory(p)));
        }
        log.info(">> 完成加载[文章分类]的数据至缓存");
    }

    protected void cacheTag() {

        List<TagDO> sources = tagRepository.list(Query.query(Criteria.where("name").exists(true)));
        if (sources.size() > 0) {
            tags = sources.stream().collect(Collectors.toMap(TagDO::getId, p -> domainMapper.toTag(p)));
        }
        log.info(">> 完成加载[标签]的数据至缓存");
    }

    /**
     * 获取门户的分类
     * @return
     */
    public List<Category> getFrontCategories() {

        List<Category> usableCategories = categories.values().stream()
                // .filter(item -> item.getArticleCount() > 0)
                .sorted(Comparator.comparingInt(Category::getSortNo))
                .collect(Collectors.toList());
        if (usableCategories.size() < 1) {
            return Collections.emptyList();
        }

        // 先找到根分类
        List<Category> snapshot = new ArrayList<>();
        List<Category> children = new ArrayList<>();

        usableCategories.forEach(item -> {
            String parentId = item.getParentId();
            if (parentId.equals(CategoryService.ROOT_NODE)) {
                snapshot.add(item);
            } else {
                children.add(item);
            }
        });
        // 子分类按父分类分组
        Map<String, List<Category>> parentChildren = children.stream().collect(Collectors.groupingBy(p -> p.getParentId()));

        snapshot.forEach(parent -> parent.setChildren(parentChildren.get(parent.getId())));
        return snapshot;
    }


    public List<Tag> getFrontTag(TagDimension dimension) {
        Objects.requireNonNull(dimension, "请指定标签的应用维度");
        return tags.values().stream()
                .filter(item -> dimension == item.getDimension())
                // .filter(item -> item.getPublishedCount() > 0)
                .collect(Collectors.toList());
    }

    public Tag getCacheTag(String id) {
        if (StringUtils.isBlank(id)) {
            return null;
        }
        return tags.get(id);
    }
}
