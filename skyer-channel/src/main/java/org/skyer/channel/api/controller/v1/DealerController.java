package org.skyer.channel.api.controller.v1;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import org.skyer.channel.api.dto.DealerDTO;
import org.skyer.channel.api.dto.DealerListDTO;
import org.skyer.channel.api.dto.ShopDeleteDTO;
import org.skyer.channel.config.ChannelSwaggerApiConfig;
import org.skyer.channel.domain.entity.Dealer;
import org.skyer.channel.domain.service.DealerService;
import org.skyer.channel.domain.vo.DealerVO;
import org.skyer.core.domain.Page;
import org.skyer.core.iam.ResourceLevel;
import org.skyer.core.util.Results;
import org.skyer.mybatis.pagehelper.domain.PageRequest;
import org.skyer.starter.keyencrypt.core.Encrypt;
import org.skyer.swagger.annotation.Permission;

/**
 * @author: lzh
 * @date: 2021-06-17
 * @description: 经销商
 */
@Api(tags = ChannelSwaggerApiConfig.DEALER_MANAGE)
@RestController("dealerController.v1")
@RequestMapping("/v1/channel/dealer")
public class DealerController {

	@Autowired
	private DealerService dealerService;

	@ApiOperation("新增经销商")
	@PostMapping("/add")
	@Permission(level = ResourceLevel.ORGANIZATION,permissionLogin = true)
	public ResponseEntity<Void> addDealer(@Valid @RequestBody Dealer dealer) {
		return dealerService.addDealer(dealer) ? Results.success() : Results.error();
	}


	@ApiOperation(value = "分页获取信息")
	@GetMapping("/list")
	@Permission(level = ResourceLevel.ORGANIZATION,permissionLogin = true)
	public ResponseEntity<Page<DealerVO>> pageList( DealerListDTO dealerListDTO, PageRequest pageRequest) {
		return Results.success( dealerService.pageDealerList(pageRequest, dealerListDTO));
	}

	@ApiOperation(value = "修改经销商信息")
	@PostMapping("/update")
	@Permission(level = ResourceLevel.ORGANIZATION,permissionLogin = true)
	public ResponseEntity<Void> updatDealer(@RequestBody DealerDTO dealerDTO) {
		return dealerService.updateDealer (dealerDTO)? Results.success():Results.error();
	}



	@ApiOperation(value = "删除经销商信息")
	@PostMapping("/delete")
	@Permission(level = ResourceLevel.ORGANIZATION,permissionLogin = true)
	public ResponseEntity<Void> deleteById(@ApiParam("主键ID") @RequestParam(value = "id") @Encrypt Long id) {
		return	dealerService.deleteById(id)? Results.success():Results.error();
	}


	@ApiOperation(value = "根据Id获取经销商信息信息")
	@GetMapping("/details")
	@Permission(level = ResourceLevel.ORGANIZATION,permissionLogin = true)
	public ResponseEntity<Dealer> getDealerDetail(@ApiParam("主键ID") @RequestParam(value = "id") @Encrypt Long id) {
		return Results.success(dealerService.getDealerDetail(id));
	}

	@ApiOperation("禁用 OR 启用")
	@PostMapping("/operation")
	@Permission(level = ResourceLevel.ORGANIZATION,permissionLogin = true)
	public ResponseEntity<Void> operation(@ApiParam("主键ID") @RequestParam(value = "id") @Encrypt Long id,
										  @ApiParam("true OR false") @RequestParam(value = "flag") Boolean flag) {
		return dealerService.operation(id, flag) ? Results.success() : Results.error();
	}

	@ApiOperation("批量删除数据")
	@PostMapping("/deletes")
	@Permission(level = ResourceLevel.ORGANIZATION, permissionLogin = true)
	public ResponseEntity<Void> deletes(@ApiParam("批量删除集合") @RequestBody @Encrypt ShopDeleteDTO shopDeleteDTO) {
		return dealerService.deletes(shopDeleteDTO) ? Results.success() : Results.error();
	}
}
