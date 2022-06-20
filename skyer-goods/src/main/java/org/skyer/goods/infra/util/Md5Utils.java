package org.skyer.goods.infra.util;


import cn.hutool.crypto.digest.MD5;

import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @description
 * @Author wusc
 * @create 2021/7/29 3:23 下午
 */
public class Md5Utils {


    public static String createMd5ByLong(List<String> attrList) {
        String collect = attrList.stream().map(String::valueOf).collect(Collectors.joining(","));
        return Base64.getEncoder().encodeToString(MD5.create().digest(collect));
    }

}
