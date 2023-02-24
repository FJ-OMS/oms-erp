package org.skyer.autoconfigure.channel;

import java.lang.annotation.*;

import org.springframework.context.annotation.Import;

/**
 * @author bojiangzhou 2018/10/25
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(ChannelAutoConfiguration.class)
public @interface EnableSkyerChannel {

}
