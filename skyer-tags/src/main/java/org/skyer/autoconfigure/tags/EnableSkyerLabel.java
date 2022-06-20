package org.skyer.autoconfigure.tags;

import java.lang.annotation.*;

import org.springframework.context.annotation.Import;

/**
 * @author bojiangzhou 2018/10/25
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(TagsAutoConfiguration.class)
public @interface EnableSkyerLabel {

}
