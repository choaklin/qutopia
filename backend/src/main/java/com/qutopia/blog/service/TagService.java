package com.qutopia.blog.service;

import com.qutopia.blog.cache.CacheUtil;
import com.qutopia.blog.entity.TagDO;
import com.qutopia.blog.entity.TagDimension;
import com.qutopia.blog.repository.TagRepository;
import com.qutopia.blog.service.domain.DomainMapper;
import com.qutopia.blog.service.domain.tag.Tag;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * 标签的服务
 *
 * @author choaklin
 * @date 2018/12/11
 */
@Service
public class TagService {

    @Autowired
    private DomainMapper domainMapper;
    @Autowired
    private CacheUtil cacheUtil;
    @Autowired
    private TagRepository tagRepository;


    public String create(TagDimension dimension, String name) {

        TagDO tagDO = new TagDO();
        tagDO.setDimension(dimension);
        tagDO.setInternal(false);
        tagDO.setName(name);
        tagDO.setReferenceCount(1);
        tagDO.setPublishedCount(1);
        return tagRepository.create(tagDO).getId();
    }

    /**
     * 更新关联的素材数
     *
     * @param id 标签ID
     * @param up 是否向上滚动，即+1
     * @param published 是否已发布
     * @return
     */
    public TagDO rollMaterialCount(TagDimension dimension, String id, String tagName, boolean up, boolean published) {

        TagDO target;
        if (StringUtils.isBlank(id)) {
            target = new TagDO();
            target.setDimension(dimension);
            target.setInternal(false);
            target.setName(tagName);
            target.setReferenceCount(1);
            if (published) {
                target.setPublishedCount(1);
            }
            tagRepository.create(target);
        } else {
            target = tagRepository.findById(id);
            Objects.requireNonNull(target, "不存在指定ID的标签");
            target.rollArticleCount(up, published);
            Update update = Update.update("referenceCount", target.getReferenceCount());
            if (published) {
                update.set("publishedCount", target.getPublishedCount());
            }
            tagRepository.updateOne(id, update);
        }
        // 更新缓存
        cacheUtil.refresh(target);
        return target;
    }


    public void reference(TagDimension dimension, String tagId, String tagName, boolean published) {

        if (StringUtils.isBlank(tagId)) {
                TagDO tagDO = new TagDO();
                tagDO.setDimension(dimension);
                tagDO.setInternal(false);
                tagDO.setName(tagName);
                tagDO.setReferenceCount(1);
                if (published) {
                    tagDO.setPublishedCount(1);
                }
                tagRepository.create(tagDO);
                cacheUtil.refresh(tagDO);
        } else {

        }
    }

    public List<Tag> list(TagDimension dimension, String name) {

        Criteria criteria = Criteria.where("dimension").is(dimension);
        if (StringUtils.isNotBlank(name)) {
            // 以输入开头匹配, 且不在乎大小写
            criteria.and("name").regex("^" + name, "i");
        }
        List<TagDO> sources = tagRepository.list(
                Query.query(criteria)
        );
        if (CollectionUtils.isNotEmpty(sources)) {

            List<Tag> dests = new ArrayList<>(sources.size());
            sources.forEach(p -> dests.add(domainMapper.toTag(p)));
            return dests;
        }
        return Collections.EMPTY_LIST;
    }

    public Tag get(String id) {

        return domainMapper.toTag(
                tagRepository.findById(id)
        );
    }

}
