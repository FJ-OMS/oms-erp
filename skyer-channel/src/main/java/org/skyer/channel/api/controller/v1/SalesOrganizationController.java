package org.skyer.channel.api.controller.v1;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import org.skyer.channel.api.dto.SalesOrganizationDTO;
import org.skyer.channel.api.dto.SalesOrganizationListDTO;
import org.skyer.channel.api.dto.ShopDeleteDTO;
import org.skyer.channel.config.ChannelSwaggerApiConfig;
import org.skyer.channel.domain.entity.SalesOrganization;
import org.skyer.channel.domain.service.SalesOrganizationService;
import org.skyer.channel.domain.vo.ListOfTreeVO;
import org.skyer.channel.domain.vo.SalesOrganizationByTenantIdVO;
import org.skyer.channel.domain.vo.SalesOrganizationListVO;
import org.skyer.core.domain.Page;
import org.skyer.core.iam.ResourceLevel;
import org.skyer.core.util.Results;
import org.skyer.mybatis.pagehelper.domain.PageRequest;
import org.skyer.starter.keyencrypt.core.Encrypt;
import org.skyer.swagger.annotation.Permission;





/**
 * <p>
 *  销售组织
 * </p>
 *
 * @author lzh
 * @since 2021-06-18
 */
@RestController("SalesOrganizationController.v1")
@RequestMapping(value = "/v1/channel/salesOrganization")
@Api(tags = ChannelSwaggerApiConfig.SALE_ORGANIZATION_MANAGE)
public class SalesOrganizationController {


    @Autowired
    private SalesOrganizationService salesOrganizationService;


    @ApiOperation(value = "分页获取信息")
    @GetMapping("/list")
    @Permission(level = ResourceLevel.ORGANIZATION,permissionLogin = true)
    public ResponseEntity<Page<SalesOrganizationListVO>> pageSalesOrganizationList(@Encrypt SalesOrganizationListDTO salesOrganizationListDTO, PageRequest pageRequest) {
        return Results.success( salesOrganizationService.pageSalesOrganizationList(pageRequest,salesOrganizationListDTO));
    }

    @ApiOperation(value = "修改销售组织")
    @PostMapping("/update")
    @Permission(level = ResourceLevel.ORGANIZATION,permissionLogin = true)
    public ResponseEntity<Void> updatSalesOrganization(@Encrypt @RequestBody SalesOrganizationDTO salesOrganizationDTO) {
        return salesOrganizationService.updateSalesOrganization (salesOrganizationDTO)? Results.success(): Results.error();
    }

    @ApiOperation(value = "新增销售组织")
    @PostMapping("/add")
    @Permission(level = ResourceLevel.ORGANIZATION,permissionLogin = true)
    public ResponseEntity<Void> createLabel(@Encrypt @RequestBody SalesOrganization  salesOrganization) {
        return salesOrganizationService.createSalesOrganization (salesOrganization)?Results.success() : Results.error();
    }

    @ApiOperation(value = "删除销售组织")
    @PostMapping("/delete")
    @Permission(level = ResourceLevel.ORGANIZATION,permissionLogin = true)
    public ResponseEntity<Void> deleteById(@ApiParam("主键ID") @RequestParam(value = "id") @Encrypt Long id) {
        return salesOrganizationService.deleteById(id) ? Results.success() : Results.error();
    }


    @ApiOperation(value = "根据Id获取销售组织信息")
    @GetMapping("/details")
    @Permission(level = ResourceLevel.ORGANIZATION,permissionLogin = true)
    public ResponseEntity<SalesOrganization> getSalesOrganizationDetail(@ApiParam("主键ID") @RequestParam(value = "id") @Encrypt Long id) {
        return Results.success(salesOrganizationService.getSalesOrganizationDetail(id));
    }

    @ApiOperation("禁用 OR 启用")
    @PostMapping("/operation")
    @Permission(level = ResourceLevel.ORGANIZATION,permissionLogin = true)
    public ResponseEntity<Void> operation(@ApiParam("主键ID") @RequestParam(value = "id") @Encrypt Long id,
                                          @ApiParam("true OR false") @RequestParam(value = "flag") Boolean flag) {
        return salesOrganizationService.operation(id, flag) ? Results.success() : Results.error();
    }

    @ApiOperation(value = "根据租户Id获取销售组织信息")
    @GetMapping("/getByTenantId")
    @Permission(level = ResourceLevel.ORGANIZATION,permissionLogin = true)
    public ResponseEntity<List<SalesOrganizationByTenantIdVO>> getsalesOrganizationByTenantId(@ApiParam("租户ID") @RequestParam(value = "tenantId") @Encrypt Long tenantId) {
        return Results.success(salesOrganizationService.getsalesOrganizationByTenantId(tenantId));
    }
    @ApiOperation("批量删除数据")
    @PostMapping("/deletes")
    @Permission(level = ResourceLevel.ORGANIZATION, permissionLogin = true)
    public ResponseEntity<Void> deletes(@ApiParam("批量删除集合") @RequestBody @Encrypt ShopDeleteDTO shopDeleteDTO) {
        return salesOrganizationService.deletes(shopDeleteDTO) ? Results.success() : Results.error();
    }


    @ApiOperation("公司列表树")
    @GetMapping("/listOfTree")
    @Permission(level = ResourceLevel.ORGANIZATION, permissionLogin = true)
    public ResponseEntity<List<ListOfTreeVO>> listOfTree(@ApiParam("true") @RequestParam(value = "flag") Boolean flag) {
        List<ListOfTreeVO> list =  salesOrganizationService.listOfTree(flag);
        return Results.success(list);
    }
}
