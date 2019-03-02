package com.qutopia.blog.utils.data.mongo;

import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import com.qutopia.blog.utils.data.mongo.mapping.LogicalRemoveField;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * todo 优化 跟ID有关的操作（findById、deleteById）的属性字段的获取的程序化，非内部写死
 *
 * @author choaklin
 * @date 2018/6/8
 * @since 1.24.0
 */
@Slf4j
public abstract class AbstractMongoRepository<T, ID extends Serializable> implements MongoRepository<T, ID>, InitializingBean {

    private final String map_reduce_filepath_prefix = "classpath:/config/mongodb/map-reduce/";
    private final String map_suffix = "_map.js";
    private final String reduce_suffix = "_reduce.js";

    /**
     * list条数最大获取量默认是1Q
     */
    private long MAX_LIST = 1000;

    protected Class<T> entityClass;
    protected String entityClassName;
    protected String collectionName;
    protected String logicalRemoveFieldName;

    @Autowired
    protected MongoOperations mongoOperations;

    /**
     * 初始化
     */
    public AbstractMongoRepository() {
        ParameterizedType parameterizedType = (ParameterizedType)this.getClass().getGenericSuperclass();
        entityClass= (Class<T>)(parameterizedType.getActualTypeArguments()[0]);
        Assert.notNull(entityClass, "未指定访问的MongoDB对象");
        entityClassName = entityClass.getName();

        Document document = entityClass.getAnnotation(Document.class);
        Assert.notNull(document, entityClassName + "未使用注解[org.springframework.data.mongodb.core.mapping.Document]");
        collectionName = document.collection();
        Assert.hasLength(collectionName, entityClassName);

        //== 获取逻辑删除的字段名
        Field[] fields = FieldUtils.getAllFields(entityClass);
        int removeFieldCount = 0;
        for (Field field : fields) {
            if (field.isAnnotationPresent(LogicalRemoveField.class)) {
                logicalRemoveFieldName = field.getName();
                removeFieldCount++;
            }
        }
        if (removeFieldCount > 1) {
            throw new IllegalArgumentException(entityClassName + "存在[" + removeFieldCount + "]的逻辑删除注解[com.fjhb6.ability.exam.util.mongo.LogicalRemoveField]");
        }
    }


    public void afterPropertiesSet() throws Exception {
        Assert.notNull(mongoOperations, "未实例化[org.springframework.data.mongodb.core.MongoTemplate]或[org.springframework.data.mongodb.core.MongoOperations]其他可用的实例");
    }

    @Override
    public T create(T t) {
        mongoOperations.insert(t, collectionName);
        return t;
    }

    public void create(Collection<T> t) {

        if (CollectionUtils.isEmpty(t)) {
            return;
        }
        mongoOperations.insert(t, entityClass);
    }

    @Override
    public void update(T t) {
        mongoOperations.save(t, collectionName);
    }

    @Override
    public long updateOne(ID id, Update update) {

        Assert.notNull(id, "未指定要修改的ID");
        UpdateResult updateResult = mongoOperations.updateFirst(
                Query.query(Criteria.where("_id").is(id)),
                update,
                collectionName
        );

        return updateResult.getModifiedCount();
    }

    @Override
    public long updateMulti(Query query, Update update) {

        UpdateResult updateResult =  mongoOperations.updateMulti(query, update, collectionName);
        return updateResult.getModifiedCount();
    }

    @Override
    public long deleteById(ID id) {

        Assert.notNull(id, "未指定要删除对象的ID");
        DeleteResult deleteResult = mongoOperations.remove(Query.query(Criteria.where("_id").is(id)), collectionName);
        return deleteResult.getDeletedCount();
    }

    @Override
    public long deleteByQuery(Query query) {

        DeleteResult deleteResult = mongoOperations.remove(query, entityClass, collectionName);
        return deleteResult.getDeletedCount();
    }

    @Override
    public Page<T> page(Pageable pageable, Query query) {
        if (StringUtils.isNotBlank(logicalRemoveFieldName)) {
            log.debug(">> 启用了注解[LogicalRemoveField], 默认追加注解字段值是{}的条件", available_true);
            query.addCriteria(Criteria.where(logicalRemoveFieldName).is(available_true));
        }

        long totalCount = mongoOperations.count(query, entityClass, collectionName);
        if (totalCount < 1) {
            return new PageImpl<T>(Collections.EMPTY_LIST, pageable, totalCount);
        }

        query.with(pageable);
        List<T> result = mongoOperations.find(query, entityClass, collectionName);
        return new PageImpl<>(result, pageable, totalCount);
    }

    @Override
    public <O> Page<O> pageAggregation(Pageable page, List<AggregationOperation> aggregationOperations, Class<O> outputClass) {
        Assert.notEmpty(aggregationOperations, "聚合管道操作序列不能为空");

        //== 增加count aggregation
        aggregationOperations.add(Aggregation.count().as(aggregation_total_count_field));
        AggregationResults<AggregationCountResult> countResults = mongoOperations.aggregate(Aggregation.newAggregation(aggregationOperations), collectionName, AggregationCountResult.class);

        AggregationCountResult countResult = countResults.getUniqueMappedResult();
        if (countResult == null) {
            return new PageImpl<>(Collections.EMPTY_LIST, page, 0L);
        }
        long totalCount = countResult.getTotalCount();
        if (totalCount < 1) {
            return new PageImpl(Collections.EMPTY_LIST, page, 0L);
        }

        //== 移除追加的count查询(最后一个)
        aggregationOperations.remove(aggregationOperations.size() - 1);

        //== 添加排序、skip、limit，执行分页查询
        aggregationOperations.add(Aggregation.sort(page.getSort()));
        aggregationOperations.add(Aggregation.skip(page.getOffset()));
        aggregationOperations.add(Aggregation.limit(page.getPageSize()));

        AggregationResults<O> outputResults = mongoOperations.aggregate(Aggregation.newAggregation(aggregationOperations), collectionName, outputClass);
        return new PageImpl<>(outputResults.getMappedResults(), page, totalCount);
    }

    @Override
    public List<T> list(Query query) {
        long count = mongoOperations.count(query, entityClass, collectionName);
        Assert.isTrue(count < MAX_LIST, "超出单次最大的获取量[" + MAX_LIST + "], 建议使用分页读取");
        return mongoOperations.find(query, entityClass);
    }

    @Override
    public T findById(ID id) {
        return this.findById(id, null);
    }

    @Override
    public T findById(ID id, String np_message) {
        T t = mongoOperations.findById(id, entityClass, collectionName);
        if (StringUtils.isNotBlank(np_message)) {
            Assert.notNull(t, np_message);
        }
        return t;
    }

    @Override
    public void deleteByIdLogically(ID id) {
        checkExistLogicalRemoveField();
        Assert.notNull(id, "未指定要删除对象的ID");

        UpdateResult result = mongoOperations.updateFirst(Query.query(Criteria.where("_id").is(id)), Update.update(logicalRemoveFieldName, available_false), collectionName);
        if (result.getMatchedCount() != 1) {
            throw new IllegalArgumentException("不存在指定逻辑删除的记录");
        }
        if (result.getModifiedCount() != 1) {
            throw new IllegalArgumentException("逻辑删除失败");
        }
    }

    @Override
    public T findUniqueOneByQuery(Query query) {

        return mongoOperations.findOne(query, entityClass, collectionName);
    }

    @Override
    public T findAvailableById(ID id) {

        return this.findAvailableById(id, null);
    }

    @Override
    public T findAvailableById(ID id, String np_message) {

        checkExistLogicalRemoveField();
        Assert.notNull(id, "未指定要查询对象的ID");

        T t =  mongoOperations.findOne(Query.query(Criteria.where(logicalRemoveFieldName).is(available_true).and("_id").is(id)), entityClass, collectionName);
        if (StringUtils.isNotBlank(np_message)) {
            Assert.notNull(t, np_message);
        }
        return t;
    }

    @Override
    public T findAvailableUniqueByQuery(Query query) {
        checkExistLogicalRemoveField();
        Assert.notNull(query, "Query对象不能为NULL");

        query.addCriteria(Criteria.where(logicalRemoveFieldName).is(available_true));
        return mongoOperations.findOne(query, entityClass, collectionName);
    }

    @Override
    public long count(Query query) {

        return mongoOperations.count(query, entityClass, collectionName);
    }

    private void checkExistLogicalRemoveField() {
        Assert.notNull(logicalRemoveFieldName, entityClassName + "类未标记逻辑删除的注解[com.fjhb6.ability.exam.util.mongo.LogicalRemoveField]");
    }

    @Override
    public long countAvailable(Query query) {
        checkExistLogicalRemoveField();
        Assert.notNull(query, "Query对象不能为NULL");

        query.addCriteria(Criteria.where(logicalRemoveFieldName).is(available_true));
        return mongoOperations.count(query, entityClass, collectionName);
    }

    public void setMAX_LIST(long maxList) {
        this.MAX_LIST = maxList;
    }


    protected String getMapFunctionPath(String moduleName, String aggregationName) {
        return getMapReduceFilePathPrefix(moduleName, aggregationName) + map_suffix;
    }

    private String getMapReduceFilePathPrefix(String moduleName, String aggregationName) {
        return map_reduce_filepath_prefix + moduleName + "/" + aggregationName;
    }

    protected String getReduceFunctionPath(String moduleName, String aggregationName) {
        return getMapReduceFilePathPrefix(moduleName, aggregationName) + reduce_suffix;
    }
}
