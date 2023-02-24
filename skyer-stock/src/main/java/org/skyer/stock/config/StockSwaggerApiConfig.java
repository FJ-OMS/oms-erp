package org.skyer.stock.config;

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
public class StockSwaggerApiConfig {
    public static final String WAREHOUSE_MANAGE = "Warehouse Manage";
    public static final String ADJUST_RECORD = "adjust record";
    public static final String CHANGE_LOG_MANAGE = "Change Log Manage";
    public static final String STORAGE_AREA_MANAGE = "Storage Area Manage";
    public static final String GOODS_STOCK_MANAGE = "Goods Stock Manage";
    public static final String OUTBOUND_DELIVERY_ORDER_MANAGE = "Outbound Delivery Order Manage";
    public static final String RESULT_RECORD = "Result record";
    public static final String WAREHOUSE_WARRANT_MANAGE = "Warehouse Warrant Manage";
    public static final String WAREHOUSE_RESULT_MANAGE = "Warehouse Result Manage";

    @Autowired
    public StockSwaggerApiConfig(Docket docket) {
        docket.tags(
                new Tag(WAREHOUSE_WARRANT_MANAGE,"入库单管理"),
                new Tag(ADJUST_RECORD,"调整单"),
                new Tag(OUTBOUND_DELIVERY_ORDER_MANAGE,"出库单管理"),
                new Tag(RESULT_RECORD,"出入库结果单"),
                new Tag(WAREHOUSE_RESULT_MANAGE,"入库结果单管理"),
                new Tag(CHANGE_LOG_MANAGE,"库存流水管理"),
                new Tag(GOODS_STOCK_MANAGE,"库存管理"),
                new Tag(STORAGE_AREA_MANAGE, "仓储管理"),
                new Tag(WAREHOUSE_MANAGE, "仓库管理")
        );
    }
}
