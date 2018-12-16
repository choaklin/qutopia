package com.qutopia.blog.service;

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
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
    private TagRepository tagRepository;

    public String create(TagDimension dimension, String name) {

        TagDO tagDO = new TagDO();
        tagDO.setDimension(dimension);
        tagDO.setInternal(false);
        tagDO.setName(name);
        tagDO.setPublishedCount(1);
        return tagRepository.create(tagDO).getId();
    }

    public List<Tag> list(String name) {

        Criteria criteria = Criteria.where("internal").is(true);
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
