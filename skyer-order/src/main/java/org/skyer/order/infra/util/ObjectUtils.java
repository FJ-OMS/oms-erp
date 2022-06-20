package org.skyer.order.infra.util;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author: lzh
 * @date: 2022-01-25
 * @description:
 */
public class ObjectUtils {

	private static Logger logger = LoggerFactory.getLogger(ObjectUtils.class);


	public static <T> List<T> objToList(Object obj, Class<T> cla){
		List<T> list = new ArrayList<T>();
		if (obj instanceof ArrayList<?>) {
			for (Object o : (List<?>) obj) {
				list.add(cla.cast(o));
			}
			return list;
		}
		return null;
	}

	/**
	 * 过滤传参中空对象
	 * @param obj
	 * @return
	 * @throws IllegalAccessException
	 */
	public static List<Field> filterEmpty(Object obj) throws IllegalAccessException {
		// 获取类对象
		Class<?> aClass = obj.getClass();
		// 获取字段
		Field[] fields = aClass.getDeclaredFields();
		List<Field> list = Arrays.asList(fields);
		List<Field> objects = Lists.newArrayList();

		list.forEach(x -> {
			x.setAccessible(true);
			try {
				if (x.get(obj) != null && x.get(obj) != "" && !"page".equals(x.getName()) && !"size".equals(x.getName())) {
					objects.add(x);
				}
			} catch (IllegalAccessException e) {
				logger.info(e.getMessage());
			}
		});
		return objects;

	}

}
