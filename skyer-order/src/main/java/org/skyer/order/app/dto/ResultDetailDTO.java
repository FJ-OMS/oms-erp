package org.skyer.order.app.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

import org.skyer.starter.keyencrypt.core.Encrypt;

/**
 * 收发货结果单详情
 *
 * @author wushaochuan 2021-08-26 17:18:17
 */
@ApiModel("收发货结果单详情")
@Data
public class ResultDetailDTO {


    //
    // 业务方法(按public protected private顺序排列)
    // ------------------------------------------------------------------------------

    //
    // 数据库字段
    // ------------------------------------------------------------------------------


    @ApiModelProperty("主键")
    @Encrypt
    private Long id;
    @ApiModelProperty(value = "结果单id")
    @Encrypt
    private Long resultId;
    @ApiModelProperty(value = "已经出入库数量")
    private BigDecimal realNum;
    @ApiModelProperty(value = "SKU编码")
    private String skuCode;
    @ApiModelProperty(value = "SKU名称")
    private String skuName;
    @ApiModelProperty(value = "商品编码")
    private String spuCode;
    @ApiModelProperty(value = "商品名称")
    private String spuName;

    //
    // 非数据库字段
    // ------------------------------------------------------------------------------

}
