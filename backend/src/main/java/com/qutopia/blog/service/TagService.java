package com.qutopia.blog.service;

import com.qutopia.blog.entity.TagDO;
import com.qutopia.blog.entity.TagDimension;
import com.qutopia.blog.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 标签的服务
 *
 * @author choaklin
 * @date 2018/12/11
 */
@Service
public class TagService {

    @Autowired
    private TagRepository tagRepository;

    public String create(TagDimension dimension, String name) {

        TagDO tagDO = new TagDO();
        tagDO.setDimension(dimension);
        tagDO.setName(name);
        tagDO.setPublishedCount(1);
        return tagRepository.create(tagDO).getId();
    }
}
