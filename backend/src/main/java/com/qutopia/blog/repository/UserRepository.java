package com.qutopia.blog.repository;

import com.qutopia.blog.entity.UserDO;
import com.qutopia.blog.utils.data.mongo.AbstractMongoRepository;
import org.springframework.stereotype.Repository;

/**
 * 用户的数据库访问对象
 *
 * @author choaklin
 * @date 2019.01.26
 */
@Repository
public class UserRepository extends AbstractMongoRepository<UserDO, String> {
}
