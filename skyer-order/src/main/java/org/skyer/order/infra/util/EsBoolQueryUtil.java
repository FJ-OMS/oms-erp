package org.skyer.order.infra.util;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * es boolean 检索工具类
 * @author ChenQuanAn
 * @date 1.0 2020/12/3 11:07 下午
 */
public class EsBoolQueryUtil {

    /**
     * 字符串"and"短语查询
     * 1. field类型为keyword: value需要与field值完全一致才能匹配成功
     * 2. field类型为text: value需要是field值的一部分才能匹配成功
     * @param builder
     * @param field
     * @param value
     * @return
     */
    public static BoolQueryBuilder andPhraseQuery(BoolQueryBuilder builder, String field, String value) {
        if(StringUtils.isNotBlank(value)) {
            builder = builder.must(new MatchPhraseQueryBuilder(field, value.trim()));
        }
        return builder;
    }

    /**
     * 字符串"and"模糊查询
     * 1. field类型为keyword: value需要与field值完全一致才能匹配成功
     * 2. field类型为text: value分词后的结果必须都存在于field值中，不要求顺序，也不要求连续
     * @param builder
     * @param field
     * @param value
     * @return
     */
    public static BoolQueryBuilder andStringQuery(BoolQueryBuilder builder, String field, String value) {
        if(StringUtils.isNotBlank(value)) {
            builder = builder.must(new SimpleQueryStringBuilder(value.trim()).field(field).defaultOperator(Operator.AND));
        }
        return builder;
    }
    /**
     * 字符串"and-not"反向模糊查询
     * 1. field类型为keyword: value需要与field值完全一致才能匹配成功
     * 2. field类型为text: value分词后的结果必须都存在于field值中，不要求顺序，也不要求连续
     * @param builder
     * @param field
     * @param value
     * @return
     */
    public static BoolQueryBuilder andNotStringQuery(BoolQueryBuilder builder, String field, String value) {
        if(StringUtils.isNotBlank(value)) {
            builder = builder.mustNot(new SimpleQueryStringBuilder(value.trim()).field(field).defaultOperator(Operator.AND));
        }
        return builder;
    }

    /**
     * 字符串"and"精确匹配
     * 1. field类型为keyword: value需要与field值完全一致才能匹配成功
     * 2. field类型为text: value必须是field分词后的某一个词才能匹配成功
     * @param builder
     * @param field
     * @param value
     * @return
     */
    public static BoolQueryBuilder andTermQuery(BoolQueryBuilder builder, String field, String value) {
        if(StringUtils.isNotBlank(value)) {
            builder = builder.must(new TermQueryBuilder(field, value.trim()));
        }
        return builder;
    }


    /**
     * 其他类型(数值，bool)"and"精确匹配，value必须与field值完全一致才能匹配成功
     * @param builder
     * @param field
     * @param value
     * @return
     */
    public static BoolQueryBuilder andTermQuery(BoolQueryBuilder builder, String field, Object value) {
        if(value != null) {
            builder = builder.must(new TermQueryBuilder(field, value));
        }
        return builder;
    }

    /**
     * 字符串通配符匹配，类似于数据库的like查询，为了性能，只能在右边加通配符
     * @param builder
     * @param field
     * @param value
     * @param wildcardChar 通配符 例如: *、?等
     * @return
     */
    public static BoolQueryBuilder andWildcardQuery(BoolQueryBuilder builder, String field, String value, String wildcardChar) {
        if(StringUtils.isNotBlank(value)) {
            builder = builder.must(new WildcardQueryBuilder(field, value + wildcardChar));
        }
        return builder;
    }

    /**
     * 字符串"and"多字段模糊查询，单个值同时匹配多个字段，多个字段之间用or查询
     * 1. field类型为keyword: value需要与field值完全一致才能匹配成功
     * 2. field类型为text: value分词后的结果必须都存在于field值中，不要求顺序，也不要求连续
     * @param builder
     * @param value
     * @param fields
     * @return
     */
    public static BoolQueryBuilder andMultiFieldStringQuery(BoolQueryBuilder builder, String value, String...fields) {
        if(StringUtils.isNotBlank(value)) {
            BoolQueryBuilder orQueryBuilder = QueryBuilders.boolQuery();
            for(String field : fields) {
                orQueryBuilder = orQueryBuilder.should(new SimpleQueryStringBuilder(value.trim()).field(field).defaultOperator(Operator.AND));
            }
            builder = builder.must(orQueryBuilder);
        }

        return builder;
    }

    /**
     * 字符串"and"多字段精确查询，单个值同时匹配多个字段，多个字段之间用or查询
     * 1. field类型为keyword: value需要与field值完全一致才能匹配成功
     * 2. field类型为text: value必须是field分词后的某一个词才能匹配成功
     * @param builder
     * @param value
     * @param fields
     * @return
     */
    public static BoolQueryBuilder andMultiFieldTermQuery(BoolQueryBuilder builder, String value, String...fields) {
        if(StringUtils.isNotBlank(value)) {
            BoolQueryBuilder orQueryBuilder = QueryBuilders.boolQuery();
            for(String field : fields) {
                orQueryBuilder = orQueryBuilder.should(new TermQueryBuilder(field, value.trim()));
            }
            builder = builder.must(orQueryBuilder);
        }

        return builder;
    }

    /**
     * 字符串通多字段通配符匹配，类似于数据库的like查询，为了性能，只能在右边加通配符
     * @param builder
     * @param value
     * @param wildcardChar 通配符 例如: *、?等
     * @return
     */
    public static BoolQueryBuilder andMultiFieldWildcardQuery(BoolQueryBuilder builder, String value, String wildcardChar, String...fields) {
        if(StringUtils.isNotBlank(value)) {
            BoolQueryBuilder orQueryBuilder = QueryBuilders.boolQuery();
            for(String field : fields) {
                orQueryBuilder = orQueryBuilder.should(new WildcardQueryBuilder(field, value.trim() + wildcardChar));
            }
            builder = builder.must(orQueryBuilder);
        }

        return builder;
    }

    /**
     * 范围查询，一般用于数值类型
     * @param builder
     * @param field
     * @param minValueExclude
     * @param maxValueInclude
     * @return
     */
    public static BoolQueryBuilder andRangeQuery(BoolQueryBuilder builder, String field, Object minValueExclude, Object maxValueInclude) {
        if(minValueExclude == null && maxValueInclude == null) {
            return builder;
        }

        RangeQueryBuilder rangeQueryBuilder = new RangeQueryBuilder(field);
        if(minValueExclude != null) {
            rangeQueryBuilder = rangeQueryBuilder.gt(minValueExclude);
        }
        if(maxValueInclude != null) {
            rangeQueryBuilder = rangeQueryBuilder.lte(maxValueInclude);
        }
        return builder.must(rangeQueryBuilder);
    }

    /**
     * 范围查询，专用于日期类型
     * @param builder
     * @param field
     * @param minValueInclude
     * @param maxValueExclude
     * @return
     */
    public static BoolQueryBuilder andRangeQuery(BoolQueryBuilder builder, String field, Date minValueInclude, Date maxValueExclude) {
        if(minValueInclude == null && maxValueExclude == null) {
            return builder;
        }

        RangeQueryBuilder rangeQueryBuilder = new RangeQueryBuilder(field);
        if(minValueInclude != null) {
            rangeQueryBuilder = rangeQueryBuilder.gte(toEsDataMillsecond(minValueInclude));
        }
        if(maxValueExclude != null) {
            rangeQueryBuilder = rangeQueryBuilder.lt(toEsDataMillsecond(maxValueExclude));
        }
        return builder.must(rangeQueryBuilder);
    }

    private static Long toEsDataMillsecond(Date date) {
        if (date == null) {
            return null;
        }
        Long second = date.getTime();
        // 查询的时候，参数传入es会少8个小时，所以在这边加上
        return second + 8 * 60 * 60 * 1000L;
    }

    /**
     * 字符串"in"模糊查询，相当于数据库的in查询
     * 1. field类型为keyword: value需要与field值完全一致才能匹配成功
     * 2. field类型为text: value分词后的结果必须都存在于field值中，不要求顺序，也不要求连续
     * @param builder
     * @param field
     * @param values
     * @return
     */
    public static BoolQueryBuilder inStringQuery(BoolQueryBuilder builder, String field, Collection<? extends String> values) {
        if(CollectionUtils.isEmpty(values)) {
            return builder;
        }
        BoolQueryBuilder shouldBuilder = new BoolQueryBuilder();
        for(String value : values) {
            if(StringUtils.isNotBlank(value)) {
                shouldBuilder = shouldBuilder.should(new SimpleQueryStringBuilder(value).field(field).defaultOperator(Operator.AND));
            }
        }
        builder = builder.must(shouldBuilder);
        return builder;
    }

    /**
     * 字符串"in"精确匹配，相当于数据库的in查询
     * 1. field类型为keyword: value需要与field值完全一致才能匹配成功
     * 2. field类型为text: value必须是field分词后的某一个词才能匹配成功
     * @param builder
     * @param field
     * @param values
     * @return
     */
    public static BoolQueryBuilder inTermQuery(BoolQueryBuilder builder, String field, Collection<? extends Object> values) {
        if(CollectionUtils.isEmpty(values)) {
            return builder;
        }
        BoolQueryBuilder shouldBuilder = new BoolQueryBuilder();
        for(Object value : values) {
            if(value != null && StringUtils.isNotBlank(value.toString())) {
                shouldBuilder = shouldBuilder.should(new TermQueryBuilder(field, value));
            }
        }
        builder = builder.must(shouldBuilder);
        return builder;
    }

    public static BoolQueryBuilder inTermQuery(BoolQueryBuilder builder, String field, Integer... values) {
        if(CollectionUtils.isEmpty(Arrays.asList(values))) {
            return builder;
        }
        BoolQueryBuilder shouldBuilder = new BoolQueryBuilder();
        for(Object value : values) {
            if(value != null && StringUtils.isNotBlank(value.toString())) {
                shouldBuilder = shouldBuilder.should(new TermQueryBuilder(field, value));
            }
        }
        builder = builder.must(shouldBuilder);
        return builder;
    }

    /**
     * 字符串"in"短语查询，相当于数据库的in查询
     * 1. field类型为keyword: value需要与field值完全一致才能匹配成功
     * 2. field类型为text: value需要是field值的一部分才能匹配成功
     * @param builder
     * @param field
     * @param values
     * @return
     */
    public static BoolQueryBuilder inPhraseQuery(BoolQueryBuilder builder, String field, Collection<? extends Object> values) {
        if(CollectionUtils.isEmpty(values)) {
            return builder;
        }
        BoolQueryBuilder shouldBuilder = new BoolQueryBuilder();
        for(Object value : values) {
            if(value != null && StringUtils.isNotBlank(value.toString())) {
                shouldBuilder = shouldBuilder.should(new MatchPhraseQueryBuilder(field, value));
            }
        }
        builder = builder.must(shouldBuilder);
        return builder;
    }

    /**
     * 字符串"in"通配符匹配，相当于数据库的in查询，通配符匹配类似于数据库的like查询，为了性能，只能在右边加通配符
     * 1. field类型为keyword: value需要与field值完全一致才能匹配成功
     * 2. field类型为text: value需要是field值的一部分才能匹配成功
     * @param builder
     * @param field
     * @param values
     * @param wildcardChar 通配符 例如: *、?等
     * @return
     */
    public static BoolQueryBuilder inWildcardQuery(BoolQueryBuilder builder, String field, Collection<? extends String> values, String wildcardChar) {
        if(CollectionUtils.isEmpty(values)) {
            return builder;
        }
        BoolQueryBuilder shouldBuilder = new BoolQueryBuilder();
        for(String value : values) {
            if(StringUtils.isNotBlank(value)) {
                shouldBuilder = shouldBuilder.should(new WildcardQueryBuilder(field, value.trim() + wildcardChar));
            }
        }
        builder = builder.must(shouldBuilder);
        return builder;
    }

    /**
     * 字符串"in"多字段短语查询，多字段的in查询，多个字段之间用or连接
     * 1. field类型为keyword: value需要与field值完全一致才能匹配成功
     * 2. field类型为text: value需要是field值的一部分才能匹配成功
     * @param builder
     * @param valueList
     * @param fields
     * @return
     */
    public static BoolQueryBuilder inMultiFieldPhraseQuery(BoolQueryBuilder builder, Collection<String> valueList, String...fields) {
        valueList = valueList == null ? null : valueList.stream().filter(StringUtils::isNotBlank).collect(Collectors.toList());
        if(CollectionUtils.isNotEmpty(valueList)) {
            BoolQueryBuilder orQueryBuilder = QueryBuilders.boolQuery();
            for(String field : fields) {
                for(String value : valueList) {
                    orQueryBuilder = orQueryBuilder.should(new MatchPhraseQueryBuilder(field, value.trim()));
                }
            }
            builder = builder.must(orQueryBuilder);
        }

        return builder;
    }

    /**
     * 字符串"in"多字段通配符匹配，多字段的in查询，多个字段之间用or连接，通配符匹配类似于数据库的like查询，为了性能，只能在右边加通配符
     * 1. field类型为keyword: value需要与field值完全一致才能匹配成功
     * 2. field类型为text: value需要是field值的一部分才能匹配成功
     * @param builder
     * @param valueList
     * @param wildcardChar 通配符 例如: *、?等
     * @param fields
     * @return
     */
    public static BoolQueryBuilder inMultiFieldWildcardQuery(BoolQueryBuilder builder, Collection<String> valueList, String wildcardChar, String...fields) {
        valueList = valueList == null ? null : valueList.stream().filter(StringUtils::isNotBlank).collect(Collectors.toList());
        if(CollectionUtils.isNotEmpty(valueList)) {
            BoolQueryBuilder orQueryBuilder = QueryBuilders.boolQuery();
            for(String field : fields) {
                for(String value : valueList) {
                    orQueryBuilder = orQueryBuilder.should(new WildcardQueryBuilder(field, value.trim() + wildcardChar));
                }
            }
            builder = builder.must(orQueryBuilder);
        }

        return builder;
    }

    /**
     * 字符串"in"多字段模糊查询，多字段的in查询，多个字段之间用or连接
     * 1. field类型为keyword: value需要与field值完全一致才能匹配成功
     * 2. field类型为text: value分词后的结果必须都存在于field值中，不要求顺序，也不要求连续
     * @param builder
     * @param valueList
     * @param fields
     * @return
     */
    public static BoolQueryBuilder inMultiFieldStringQuery(BoolQueryBuilder builder, Collection<String> valueList, String...fields) {
        valueList = valueList == null ? null : valueList.stream().filter(StringUtils::isNotBlank).collect(Collectors.toList());
        if(CollectionUtils.isNotEmpty(valueList)) {
            BoolQueryBuilder orQueryBuilder = QueryBuilders.boolQuery();
            for(String field : fields) {
                for(String value : valueList) {
                    orQueryBuilder = orQueryBuilder.should(new SimpleQueryStringBuilder(value.trim()).field(field).defaultOperator(Operator.AND));
                }
            }
            builder = builder.must(orQueryBuilder);
        }

        return builder;
    }

    /**
     * 字符串"or"模糊查询
     * 1. field类型为keyword: value需要与field值完全一致才能匹配成功
     * 2. field类型为text: value分词后的结果必须都存在于field值中，不要求顺序，也不要求连续
     * @param builder
     * @param field
     * @param value
     * @return
     */
    public static BoolQueryBuilder orStringQuery(BoolQueryBuilder builder, String field, String value) {
        if(StringUtils.isNotBlank(value)) {
            builder = builder.should(new SimpleQueryStringBuilder(value).field(field).defaultOperator(Operator.AND));
        }
        return builder;
    }

    /**
     * 字符串"or"精确匹配，相当于数据库的in查询
     * 1. field类型为keyword: value需要与field值完全一致才能匹配成功
     * 2. field类型为text: value必须是field分词后的某一个词才能匹配成功
     * @param builder
     * @param field
     * @param value
     * @return
     */
    public static BoolQueryBuilder orTermQuery(BoolQueryBuilder builder, String field, Object value) {
        if(value != null && StringUtils.isNotBlank(value.toString())) {
            builder = builder.should(new TermQueryBuilder(field, value));
        }
        return builder;
    }

    /**
     * 字符串"not"模糊查询
     * 1. field类型为keyword: value需要与field值完全不一致才能匹配成功
     * 2. field类型为text: value分词后的结果必须都不存在于field值中，不要求顺序，也不要求连续
     * @param builder
     * @param field
     * @param value
     * @return
     */
    public static BoolQueryBuilder notStringQuery(BoolQueryBuilder builder, String field, String value) {
        if(StringUtils.isNotBlank(value)) {
            builder = builder.mustNot(new SimpleQueryStringBuilder(value).field(field).defaultOperator(Operator.AND));
        }
        return builder;
    }

    /**
     * 字符串"not"精确匹配，相当于数据库的in查询
     * 1. field类型为keyword: value需要与field值完全不一致才能匹配成功
     * 2. field类型为text: value必须不是field分词后的某一个词才能匹配成功
     * @param builder
     * @param field
     * @param value
     * @return
     */
    public static BoolQueryBuilder notTermQuery(BoolQueryBuilder builder, String field, Object value) {
        if(value != null && StringUtils.isNotBlank(value.toString())) {
            builder = builder.mustNot(new TermQueryBuilder(field, value));
        }
        return builder;
    }

    /**
     * 字符串"not in"模糊查询，相当于数据库的not in查询
     * 1. field类型为keyword: value需要与field值完全不一致才能匹配成功
     * 2. field类型为text: value分词后的结果必须都不存在于field值中，不要求顺序，也不要求连续
     * @param builder
     * @param field
     * @param values
     * @return
     */
    public static BoolQueryBuilder notInStringQuery(BoolQueryBuilder builder, String field, Collection<? extends String> values) {
        if(values != null) {
            for(String value : values) {
                if(StringUtils.isNotBlank(value)) {
                    builder = builder.mustNot(new SimpleQueryStringBuilder(value).field(field).defaultOperator(Operator.AND));
                }
            }
        }
        return builder;
    }

    /**
     * 字符串"not in"精确匹配，相当于数据库的not in查询
     * 1. field类型为keyword: value需要与field值完全不一致才能匹配成功
     * 2. field类型为text: value必须不是field分词后的某一个词才能匹配成功
     * @param builder
     * @param field
     * @param values
     * @return
     */
    public static BoolQueryBuilder notInTermQuery(BoolQueryBuilder builder, String field, Collection<? extends Object> values) {
        if(values != null) {
            for(Object value : values) {
                if(value != null && StringUtils.isNotBlank(value.toString())) {
                    builder = builder.mustNot(new TermQueryBuilder(field, value));
                }
            }
        }
        return builder;
    }

    public static BoolQueryBuilder notNull(BoolQueryBuilder builder, String field) {
        return builder.must(new ExistsQueryBuilder(field));
    }

    /**
     * 设置分页
     * @param builder
     * @return
     */
    public static SearchSourceBuilder page(SearchSourceBuilder builder, int number,int size) {
        int from = (number ) * size;
        return builder.from(from).size(size);
    }

    /**
     * 设置排序，支持多字段排序，排序字段list中的每一项为一个排序字段，格式为: field,asc/desc。例如：createDate,asc 表示按创建时间升序排序。注意: text类型的字段不能做为排序字段。
     * @param builder
     * @param orders
     * @return
     */
    public static SearchSourceBuilder order(SearchSourceBuilder builder, List<String> orders) {
        if(CollectionUtils.isEmpty(orders)) {
            return builder;
        }

        for (String order : orders) {
            String[] orderData = order.split(",");
            String field = StringUtils.trimToEmpty(orderData[0]);
            String sortType = orderData.length > 1 ? StringUtils.trimToEmpty(orderData[1]) : "";
            builder = builder.sort(field, SortOrder.DESC.name().equalsIgnoreCase(sortType) ? SortOrder.DESC : SortOrder.ASC);
        }
        return builder;
    }
}
