package org.skyer.goods.config;

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
public class GoodsSwaggerApiConfig {

    public static final String GOODS_CATEGORY = "Goods Category";
    public static final String GOODS_CATEGORY_ATTRIBUTE = "Goods Category Attribute";
    public static final String GOODS_BRAND = "goods-brand";
    public static final String GOODS_MANAGER = "goods-manager";
    public static final String GOODS_ATTRIBUTE = "goods-attribute";
    public static final String GOODS_ATTRIBUTE_RELATION = "goods-attribute-relation";
    public static final String GOODS_MATERIAL = "goods-material";
    public static final String GOODS_SPECIFICATIONS = "goods-specifications";
    public static final String GOODS_SKU = "goods-sku";
    public static final String GOODS_PRICE = "goods-price";
    public static final String GOODS_PRICE_SETTING = "goods-price-setting";
    public static final String GOODS_GROUP = "goods group";
    public static final String GOODS_SPU_SON_MUM = "goods-spu-son";
    public static final String GOODS_INSTALL_SERVICE = "goods-install-service";
    public static final String GOODS_SPU_INSTALL_SERVICE = "goods-spu-install-service";
    public static final String GOODS_SKU_AUDIT_SETTING = "goods-sku-audit-setting";
    public static final String GOODS_SKU_AUDIT_MANAGER = "goods-sku-audit-manager";

    @Autowired
    public GoodsSwaggerApiConfig(Docket docket) {
        docket.tags(
                new Tag(GOODS_CATEGORY, "商品分类管理"),
                new Tag(GOODS_CATEGORY_ATTRIBUTE, "商品分类属性管理"),
                new Tag(GOODS_BRAND, "商品品牌"),
                new Tag(GOODS_MANAGER, "商品管理"),
                new Tag(GOODS_ATTRIBUTE, "商品属性"),
                new Tag(GOODS_MATERIAL, "商品素材管理"),
                new Tag(GOODS_SPECIFICATIONS, "商品规格管理"),
                new Tag(GOODS_SKU, "商品SKU"),
                new Tag(GOODS_PRICE, "商品价格管理"),
                new Tag(GOODS_PRICE_SETTING, "商品价格项设置"),
                new Tag(GOODS_PRICE, "商品价格管理"),
                new Tag(GOODS_SPU_SON_MUM, "子母商品管理"),
                new Tag(GOODS_INSTALL_SERVICE, "安装服务管理"),
                new Tag(GOODS_SPU_INSTALL_SERVICE, "商品安装服务管理"),
                new Tag(GOODS_ATTRIBUTE_RELATION, "商品属性关联"),
                new Tag(GOODS_GROUP, "商品组合"),
                new Tag(GOODS_SKU_AUDIT_MANAGER, "商品SKU审核管理"),
                new Tag(GOODS_SKU_AUDIT_SETTING, "商品SKU审核设置")
        );
    }

}
