package org.skyer.autoconfigure.goods;

import java.lang.annotation.*;

import org.springframework.context.annotation.Import;

/**
 * @author bojiangzhou 2018/10/25
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(GoodsAutoConfiguration.class)
public @interface EnableSkyerGoods {

}
