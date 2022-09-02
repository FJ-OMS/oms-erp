package org.skyer.gateway.common;

import java.util.Set;

/**
 * 只读列表
 * 
 * @author XCXCXCXCX
 * @version 1.2.0
 * @date 2019/12/5 7:41 下午
 */
public interface SetRepository<T> {

    boolean isEnable();

    Set<T> get();

    boolean contains(String key);

}
