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
public class AfterItemAddDTO {

	@ApiModelProperty(value = "退款单头id")
	@NotNull
	@Encrypt
	private Long id;

	@ApiModelProperty(value = "商品")
	private List<AfterSaleItem> items;

	@ApiModelProperty(value = "商品行")
	private List<AfterSaleItemLine> itemLines;
}
