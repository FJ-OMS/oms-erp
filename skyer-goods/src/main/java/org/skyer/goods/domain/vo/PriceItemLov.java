package org.skyer.goods.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.skyer.boot.platform.lov.dto.LovValueDTO;

import java.io.Serializable;
import java.util.List;

/**
 * 租户价格项LOV
 *
 * @author chenzz
 */
@Data
@ApiModel("租户价格项值集")
public class PriceItemLov implements Serializable {

    @ApiModelProperty("租户价格项类型")
    List<LovValueDTO> priceItemTypes;

}
