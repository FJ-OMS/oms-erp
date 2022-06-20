package org.skyer.goods.infra.util;


import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * List集合工具类
 * @author xurongfa 2021-01-04 12:33
 */
public class ListUtils{

    /**
     * 根据 func函数返回值，提取list的重复元素
     * @param list 待提取的对象集合
     * @param func 处理对象的函数
     * @param <T> 入参对象类型
     * @param <K> 出参类型，func结果类型
     * @return 重复的func结果
     */
    public static <T,K> List<K> getRepeatList(List<T> list, Function<? super T, ? extends K > func){
        if (Objects.isNull(list)){
            return new ArrayList<>();
        }
        return list.stream()
                .map(func)
                .filter(Objects::nonNull)
                .collect(Collectors.toMap(x -> x, x -> 1, Integer::sum)) //记录出现次数
                .entrySet().stream()
                .filter(x->x.getValue() > 1) //过滤重复的属性
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    /**
     * List 转其他类型List
     */
    public static <T> List<T> listConvert(List<?> lists, Class<T> clzz) {
        List<T> vos = new ArrayList<>();
        if (CollectionUtils.isEmpty(lists)) {
            return vos;
        }
        try {
            vos = lists.stream().map(e -> {
                try {
                    T t = clzz.newInstance();
                    BeanUtils.copyProperties(e, t);
                    return t;
                } catch (InstantiationException | IllegalAccessException ex) {
                    ex.printStackTrace();
                }
                return null;
            }).collect(Collectors.toList());
            return vos;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
