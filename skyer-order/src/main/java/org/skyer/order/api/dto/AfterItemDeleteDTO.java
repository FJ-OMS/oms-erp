package org.skyer.order.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.skyer.order.domain.entity.AfterSaleItem;
import org.skyer.order.domain.entity.AfterSaleItemLine;
import org.skyer.starter.keyencrypt.core.Encrypt;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author: zhanglongbing
 */
@Data
public class AfterItemDeleteDTO {

	@ApiModelProperty(value = "退款商品")
	private List<AfterSaleItem> itemList;

	@ApiModelProperty(value = "是否仅退款 true:仅退款 false:退款退货")
	@NotNull(message = "是否仅退款不能为空")
	private Boolean isOnlyRefund;

}
