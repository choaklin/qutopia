package com.qutopia.blog.utils.mongo;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标记是逻辑删除的字段, 逻辑删除的时候使用
 *
 * @author choaklin
 * @since 2018.7.29
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface LogicalRemoveField {
}
