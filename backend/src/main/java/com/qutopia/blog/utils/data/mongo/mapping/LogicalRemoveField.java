package com.qutopia.blog.utils.data.mongo.mapping;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标记是逻辑删除的字段, 逻辑操作（查询、删除）的时候使用
 *
 * @author choaklin
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface LogicalRemoveField {
}
