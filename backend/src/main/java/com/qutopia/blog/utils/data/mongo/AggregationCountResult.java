package com.qutopia.blog.utils.data.mongo;

import lombok.Data;

/**
 * 聚合查询的通用统计的返回对象
 *
 * @author choaklin
 * @date 2018/6/30
 * @since 1.24.0
 */
@Data
public class AggregationCountResult {

    /**
     * @see MongoRepository#aggregation_total_count_field
     */
    private long totalCount;
}
