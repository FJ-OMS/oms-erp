package org.skyer.goods.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.skyer.starter.keyencrypt.core.Encrypt;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

/**
 * 子母商品
 *
 * @author chenzz 2021-08-16 15:04:38
 */
@ApiModel("安装服务")
@Data
public class InstallServiceDTO {

    @ApiModelProperty("主键id")
    @Encrypt
    private Long id;

    @ApiModelProperty(value = "安装服务编码", required = true)
    // @NotEmpty(message = "安装服务编码不能为空")
    private String serviceCode;

    @ApiModelProperty(value = "服务名称", required = true)
    @NotEmpty(message = "服务名称不能为空")
    private String name;

    @ApiModelProperty(value = "安装类别", required = true)
    @NotEmpty(message = "安装类别不能为空")
    private String type;

    @ApiModelProperty(value = "配备安装人数")
    @NotNull(message = "配备安装人数不能为空")
    private Long peopleAmount;

    @ApiModelProperty(value = "费用")
    @NotNull(message = "费用不能为空")
    private BigDecimal money;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "服务地区，json数据")
    private List<ServiceZoneDTO> serviceZone;

    private Long objectVersionNumber;
}
