package org.skyer.order.config;

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
public class OrderSwaggerApiConfig {
    /**
     * 订单
     */
    public static final String ORDER_HEADER = "Order Header";
    public static final String ORDER_REMOTE = "Order REMOTE";
    public static final String ORDER_FLOW_CONFIG = "Order Flow Config";
    public static final String PACKAGE_SETTING_SINGLE_GOODS = "package Setting Single Goods";
    public static final String GOODS_LINE_AUDIT_SETTING = "goods-line-audit-setting";


    @Autowired
    public OrderSwaggerApiConfig(Docket docket) {
        docket.tags(
                new Tag(ORDER_HEADER, "订单"),
                new Tag(ORDER_REMOTE, "订单-接收通知"),
                new Tag(ORDER_FLOW_CONFIG, "订单流程配置"),
                new Tag(GOODS_LINE_AUDIT_SETTING, "商品行审核配置管理"),
                new Tag(PACKAGE_SETTING_SINGLE_GOODS, "单品发包设置")
        );
    }

}
