package org.skyer.order.domain.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.skyer.mybatis.domain.AuditDomain;
import java.math.BigDecimal;
import org.skyer.mybatis.annotation.ModifyAudit;
import org.skyer.mybatis.annotation.VersionAudit;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 内部订单明细价格（聚合）
 *
 * @author wushaochuan 2022-01-18 14:34:54
 */
@ApiModel("内部订单明细价格（聚合）")
@ModifyAudit
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@Table(name = "sord_item_price")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemPrice extends AuditDomain {

    public static final String FIELD_ID = "id";
    public static final String FIELD_OUTER_ORDER_NO = "outerOrderNo";
    public static final String FIELD_INNER_ORDER_NO = "innerOrderNo";
    public static final String FIELD_ORDER_ITEM_ID = "orderItemId";
    public static final String FIELD_TYPE = "type";
    public static final String FIELD_NAME = "name";
    public static final String FIELD_AMOUNT = "amount";
    public static final String FIELD_DELETE_FLAG = "deleteFlag";

    //
    // 业务方法(按public protected private顺序排列)
    // ------------------------------------------------------------------------------

    //
    // 数据库字段
    // ------------------------------------------------------------------------------


    @ApiModelProperty("主键id")
    @Id
    @GeneratedValue
    private Long id;
    @ApiModelProperty(value = "平台订单号",required = true)
    @NotBlank
    private String outerOrderNo;
    @ApiModelProperty(value = "内部订单号",required = true)
    @NotBlank
    private String innerOrderNo;
    @ApiModelProperty(value = "订单商品明细id",required = true)
    @NotNull
    private Long orderItemId;
    @ApiModelProperty(value = "价格类型",required = true)
    @NotBlank
    private String type;
    @ApiModelProperty(value = "商品明细价格项名称",required = true)
    @NotBlank
    private String name;
    @ApiModelProperty(value = "金额",required = true)
    @NotNull
    private BigDecimal amount;
    @ApiModelProperty(value = "系统字段：删除标识",required = true)
    @NotNull
    private Integer deleteFlag;


}
