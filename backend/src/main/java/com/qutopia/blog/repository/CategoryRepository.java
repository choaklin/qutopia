package com.qutopia.blog.repository;

import com.qutopia.blog.entity.CategoryDO;
import com.qutopia.blog.utils.data.mongo.AbstractMongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @author choaklin
 * @date 2018.12.05
 */
@Repository
public class CategoryRepository extends AbstractMongoRepository<CategoryDO, String> {
}
