package org.skyer.order.infra.annocations;

import java.lang.annotation.*;

/**
 * 数据源类型
 *
 * @author
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
