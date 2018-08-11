package com.qutopia.blog.repository;

import com.qutopia.blog.entity.CommentDO;
import com.qutopia.blog.utils.data.mongo.AbstractMongoRepository;
import org.springframework.stereotype.Repository;

/**
 * 评论的数据访问对象
 *
 * @author choaklin
 * @version 0.0.1
 * @date 2018/8/11
 */
@Repository
public class CommentRepository extends AbstractMongoRepository<CommentDO, String> {
}
