package com.qutopia.blog.gateway.web.admin;


import com.qutopia.blog.entity.TagDimension;
import com.qutopia.blog.service.TagService;
import com.qutopia.blog.service.domain.tag.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("tagManage")
public class TagManageController {

    @Autowired
    private TagService tagService;

    @GetMapping(value = "list")
    public List<Tag> list(TagDimension dimension, String name) {

        return tagService.list(dimension, name);
    }
}
