package com.qutopia.blog.repository;

import com.qutopia.blog.entity.TagDO;
import com.qutopia.blog.utils.data.mongo.AbstractMongoRepository;
import org.springframework.stereotype.Repository;

/**
 * 标签的数据访问对象
 *
 * @author choaklin
 * @date 2018/12/11
 */
@Repository
public class TagRepository extends AbstractMongoRepository<TagDO, String> {
}
