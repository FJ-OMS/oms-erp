package org.skyer.stock.domain.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.math.BigDecimal;
import java.util.List;

import org.skyer.starter.keyencrypt.core.Encrypt;

/**
 * 出库商品详情
 *
 * @author lixiaoyang 2021-10-15 15:07:08
 */
@ApiModel("出库商品详情")
@Data
@Table(name = "sstock_outbound_delivery_order_detail")
public class OutboundDeliveryOrderDetail {

    public static final String FIELD_ID = "id";
    public static final String FIELD_ODO_ID = "odoId";
    public static final String FIELD_SKU_CODE = "skuCode";
    public static final String FIELD_SKU_NAME = "skuName";
    public static final String FIELD_SPU_CODE = "spuCode";
    public static final String FIELD_SPU_NAME = "spuName";
    public static final String FIELD_CATEGORY_NAME = "categoryName";
    public static final String FIELD_PLANNED_QTY = "plannedQty";
    public static final String FIELD_ALREADY_OUT_QTY = "alreadyOutQty";
    public static final String FIELD_WAIT_OUT_QTY = "waitOutQty";
    public static final String FIELD_CANCEL_QTY = "cancelQty";

    //
    // 业务方法(按public protected private顺序排列)
    // ------------------------------------------------------------------------------
    /**
     * 默认参数赋值
     * @author lixiaoyang
     * @date 2021/10/15 17:38
     */
    public void preset() {
        this.alreadyOutQty = BigDecimal.valueOf(0);
        this.waitOutQty = BigDecimal.valueOf(0);
        this.cancelQty = BigDecimal.valueOf(0);
    }
    //
    // 数据库字段
    // ------------------------------------------------------------------------------

    @ApiModelProperty("主键")
    @Id
    @GeneratedValue
    @Encrypt
    private Long id;
    @ApiModelProperty(value = "出库单ID")
    @Encrypt
    private Long odoId;
    @ApiModelProperty(value = "商品SKU编码")
    private String skuCode;
    @ApiModelProperty(value = "商品SKU名称")
    private String skuName;
    @ApiModelProperty(value = "商品编码")
    private String spuCode;
    @ApiModelProperty(value = "商品名称")
    private String spuName;
    @ApiModelProperty(value = "商品分类名称")
    private String categoryName;
    @ApiModelProperty(value = "计划数量")
    private BigDecimal plannedQty;
    @ApiModelProperty(value = "已出库数量")
    private BigDecimal alreadyOutQty;
    @ApiModelProperty(value = "待出库数量")
    private BigDecimal waitOutQty;
    @ApiModelProperty(value = "取消数量")
    private BigDecimal cancelQty;

    //
    // 非数据库字段
    // ------------------------------------------------------------------------------

    @ApiModelProperty(value = "订单商品明细平铺ID集合")
    @Transient
    private List<Long> orderGoodsLineIds;
}
