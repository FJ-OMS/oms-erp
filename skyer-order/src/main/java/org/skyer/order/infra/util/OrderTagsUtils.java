package org.skyer.order.infra.util;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.skyer.order.domain.repository.HeaderRepository;
import org.skyer.order.infra.common.CommonConstants;
import org.springframework.stereotype.Component;

/**
 * @author: lzh
 * @date: 2022-04-07
 * @description: 打标工具
 */
public class OrderTagsUtils {
	/**
	 * 订单打标
	 * @param originTags
	 * @param tagsFlag
	 * @return
	 */
	public static String dealTags(String originTags,boolean tagsFlag,List<String> needTags) {


		// 移除标签
		if (!tagsFlag) {
			String newTags = "";
			if (StringUtils.isNotBlank(originTags)) {
				List<String> stringList = Arrays.asList(originTags.split(","));
				List<String> arrList = new ArrayList<>(stringList);
				// 移除传入的标签
				arrList.removeAll(needTags);
				if (!arrList.isEmpty()) {
					newTags = arrList.stream().
							reduce(new StringBuilder(), (sb, s) -> sb.append(s).append(','), StringBuilder::append).toString();
					newTags = newTags.substring(0, newTags.length() - 1);
				}
				return newTags;
			}
			return newTags;
		}

		// 添加标签
		if (StringUtils.isNotBlank(originTags)) {
			// 已有标
			List<String> stringList = Arrays.asList(originTags.split(","));
			List<String> arrList = new ArrayList<>(stringList);
			arrList.addAll(stringList);
			arrList.addAll(needTags);
			// 去重
			List<String> addTags = arrList.stream().distinct().collect(Collectors.toList());
			return StringUtils.join(addTags,",");
		} else {
			return StringUtils.join(needTags,",");
		}

	}
}
