package org.skyer.autoconfigure.afterSales;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author bojiangzhou 2018/10/25
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(AfterSalesAutoConfiguration.class)
public @interface EnableSkyerAfterSales {
}
