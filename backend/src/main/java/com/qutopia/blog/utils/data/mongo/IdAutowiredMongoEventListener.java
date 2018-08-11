package com.qutopia.blog.utils.data.mongo;

import com.qutopia.blog.utils.data.commons.UUIDGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

/**
 * MongoDB实体的ID自动注入
 *
 * @author choaklin
 * @date 2018/7/29
 * @since 0.0.1
 */
@Slf4j
@Component
public class IdAutowiredMongoEventListener extends AbstractMongoEventListener<Object> {

    @Override
    public void onBeforeConvert(BeforeConvertEvent<Object> event) {

        final Object source = event.getSource();
        if (source != null) {
            //== 对[id]、[rootId]进行赋值
            String entityId = UUIDGenerator.generate();
            ReflectionUtils.doWithFields(source.getClass(), field -> {
                ReflectionUtils.makeAccessible(field);
                /**
                 * 使用{@link org.springframework.data.annotation.Id}注解且属性值是null
                 */
                if (field.isAnnotationPresent(Id.class) && field.get(source) == null) {
                    // 设置自增ID
                    field.set(source, entityId);
                    log.debug(">> 字段[{}]自动注入的Id是: {}", field.getName(), entityId);
                }
            });
        }
    }
}
