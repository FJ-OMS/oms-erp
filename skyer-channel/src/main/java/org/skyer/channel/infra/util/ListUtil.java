package org.skyer.channel.infra.util;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * List转换工具类
 *
 * @author lzh
 * @since 2021-06-22
 **/
public class ListUtil {
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

    /**
     * List转String
     *
     * @param lists     传入的List
     * @param separator 分隔符(可以为空)
     * @return String
     */
    public static String ListToStr(List<?> lists, String separator) {
        if (CollectionUtils.isEmpty(lists)) {
            return null;
        }
        StringBuffer sb = new StringBuffer();
        for (Object list : lists) {
            sb.append(list);
            if (StringUtils.isNotEmpty(separator)) {
                sb.append(separator);
            }
        }
        String str = sb.toString();
        return StringUtils.isEmpty(separator) ? str : str.substring(0, str.length() - 1);
    }
}
