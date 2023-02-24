package org.skyer.afterSales.config;

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
public class AfterSalesSwaggerApiConfig {
    public static final String USER_EXPRESSAGE_ACCOUNT_MANAGE = "User Expressage Account Manage";
    public static final String INSTALL_ORDERS = "Install Orders";


    @Autowired
    public AfterSalesSwaggerApiConfig(Docket docket) {
        docket.tags(
                new Tag(USER_EXPRESSAGE_ACCOUNT_MANAGE, "用户快递账号管理"),
                new Tag(INSTALL_ORDERS, "安装工单")
        );
    }
}
