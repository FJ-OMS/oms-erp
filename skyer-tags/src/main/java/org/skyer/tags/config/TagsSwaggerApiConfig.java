package org.skyer.tags.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.service.Tag;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * <p>
 * Swagger Api 描述配置
 * </p>
 *
 * @author qingsheng.chen 2018/7/30 星期一 14:26
 */
@Configuration
public class TagsSwaggerApiConfig {
    public static final String ORDER_LABEL_MANAGE = "Label Manage";

    @Autowired
    public TagsSwaggerApiConfig(Docket docket) {
        docket.tags(
                new Tag(ORDER_LABEL_MANAGE, "订单标签管理")
        );
    }
}
