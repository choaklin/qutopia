package com.qutopia.blog.utils.mongo;

import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @author choaklin
 * @date 2018/6/8
 * @since 1.24.0
 */
public interface MongoRepository<T, ID extends Serializable> {

    int available_yes = 1;
    int available_no = 0;

    String aggregation_total_count_field = "totalCount";

    /**
     * 新增新的记录
     *
     * @param t
     */
    T create(T t);

    void create(Collection<T> t);

    void update(T t);

    /**
     * 修改单个文档的指定属性数据，适合对大文档的局部属性修改的操作
     * @param id
     * @param update
     */
    long updateOne(ID id, Update update);

    long updateMulti(Query query, Update update);

    long deleteById(ID id);

    long deleteByQuery(Query query);

    /**
     * 如果实体有注解{@link LogicalRemoveField}, 内置增加该字段值为{@link #available_yes}的条件
     * @param page
     * @param query
     * @return
     */
    // Page<T> page(Page page, Query query);

    /**
     * <p>聚合管道的分页查询
     * <p>未内置为逻辑字段(有注解{@link LogicalRemoveField})增加值为{@link #available_yes}的条件
     * <p>有排序需求，设置在{@link Page#sortPolicy}
     *
     * @param page
     * @param aggregationOperations 聚合管道操作
     * @param outputClass
     * @return
     */
    // <O> Page<O> pageAggregation(Page page, List<AggregationOperation> aggregationOperations, Class<O> outputClass);

    List<T> list(Query query);

    T findById(ID id);

    /**
     *
     * @param id 期望查询的ID对象
     * @param np_message 非空白则开启校验, 为null则抛异常
     * @return
     * @see org.springframework.util.Assert#notNull(Object, String)
     */
    T findById(ID id, String np_message);

    void deleteByIdLogically(ID id);

    T findUniqueOneByQuery(Query query);

    T findAvailableById(ID id);

    T findAvailableById(ID id, String np_message);

    T findAvailableUniqueByQuery(Query query);

    long count(Query query);

    long countAvailable(Query query);
}
