package org.skyer.goods.infra.annocations;

import java.lang.annotation.*;

/**
 * 数据源类型
 *
 *
 */
@Inherited
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DatasourceType {

    /**
     * 数据源类型
     *
     * @return 类型
     */
    String[] value();

}
