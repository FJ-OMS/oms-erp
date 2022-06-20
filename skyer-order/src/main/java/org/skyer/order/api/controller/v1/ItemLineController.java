package org.skyer.order.api.controller.v1;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import org.skyer.core.domain.Page;
import org.skyer.core.iam.ResourceLevel;
import org.skyer.core.util.Results;
import org.skyer.order.api.dto.ShopIdsAndSkuCodesDTO;
import org.skyer.order.api.vo.ShopSkuStockoutVO;
import org.skyer.order.domain.entity.Item;
import org.skyer.order.domain.repository.ItemLineRepository;
import org.skyer.starter.keyencrypt.core.Encrypt;
import org.skyer.swagger.annotation.Permission;

/**
 *  商品行管理 API
 * @author: lzh
 * @date: 2022-02-24
 * @description:
 */
@RestController("itemLineController.v1")
@RequestMapping("/v1/itemLine")
public class ItemLineController {


	@Autowired
	private ItemLineRepository itemLineRepository;



	@ApiOperation(value = "查询店铺缺货SKU信息")
	@Permission(level = ResourceLevel.SITE)
	@PostMapping("store-stockout")
	public ResponseEntity<List<ShopSkuStockoutVO>> storeStockout(@RequestBody ShopIdsAndSkuCodesDTO shopIdsAndSkuCodesDTO) {
		List<ShopSkuStockoutVO> vos = itemLineRepository.storeStockout(shopIdsAndSkuCodesDTO);
		return Results.success(vos);
	}
}
