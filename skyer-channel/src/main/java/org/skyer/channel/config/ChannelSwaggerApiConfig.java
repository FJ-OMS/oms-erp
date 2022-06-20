package org.skyer.channel.config;

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
public class ChannelSwaggerApiConfig {
    public static final String SHOP_MANAGE = "Shop Manage";
    public static final String ONLINE_SHOP_MANAGE = "Online Shop Manage";
    public static final String PHYSICAL_SHOP_MANAGE = "physical Shop Manage";
    public static final String DEALER_MANAGE="Dealer Manage";
    public static final String SALE_ORGANIZATION_MANAGE= "SalesOrganization Manage";
    public static final String WAREHOUSE_MANAGE="Warehouse Manage";


    @Autowired
    public ChannelSwaggerApiConfig(Docket docket) {
        docket.tags(
                new Tag(SHOP_MANAGE, "店铺公共接口"),
                new Tag(ONLINE_SHOP_MANAGE, "网店管理"),
                new Tag(PHYSICAL_SHOP_MANAGE, "门店管理"),
                new Tag(DEALER_MANAGE, "经销商管理"),
                new Tag(SALE_ORGANIZATION_MANAGE, "销售组织管理"),
                new Tag(WAREHOUSE_MANAGE, "渠道仓库管理")

        );
    }
}
