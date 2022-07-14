package org.skyer.channel.infra.convertor;

import java.util.List;
import java.util.Map;

import org.skyer.core.convert.CommonConverter;

/**
 * 通用对象转换工具
 *
 * @author
 */
public class CommonConvertor<T, V> {
    /**
     * List对象转换
     *
     * @param targetClazz 目标对象class
     * @param sourceList 源对象list
     * @param <T>
     * @param <V>
     * @return
     */
    public static <T, V> List<T> listConvertor(Class<T> targetClazz, List<V> sourceList) {
        return listConvertor(targetClazz, sourceList, null);
    }

    /**
     * 
     * List对象转换
     * 
     * @param targetClazz
     * @param sourceList
     * @param rule 自定义映射规则(target字段,source字段)
     * @return
     */
    public static <T, V> List<T> listConvertor(Class<T> targetClazz, List<V> sourceList, Map<String, String> rule) {
        return CommonConverter.listConverter(targetClazz, sourceList, rule);
    }

    /**
     * List对象转换(过滤当前多语言)
     * 
     * @param targetClazz
     * @param field 多语言字段名
     * @param sourceList
     * @return
     */
    public static <T, V> List<T> listConvertor(Class<T> targetClazz, String field, List<V> sourceList) {
        return listConvertor(targetClazz, field, sourceList, null);
    }

    /**
     * 
     * description
     * 
     * @param targetClazz
     * @param field 多语言字段名
     * @param sourceList
     * @param rule 自定义映射规则(target字段,source字段)
     * @return
     */
    public static <T, V> List<T> listConvertor(Class<T> targetClazz, String field, List<V> sourceList,
                    Map<String, String> rule) {
        return CommonConverter.listConverter(targetClazz, field, sourceList, rule);
    }


    /**
     * javaBean对象转换
     * 
     * @param targetClazz
     * @param sourceObject
     * @param <T>
     * @param <V>
     * @return
     */
    public static <T, V> T beanConvert(Class<T> targetClazz, V sourceObject) {
        return CommonConverter.beanConvert(targetClazz, sourceObject);
    }
}
