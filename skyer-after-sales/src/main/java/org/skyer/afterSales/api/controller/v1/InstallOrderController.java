package org.skyer.afterSales.api.controller.v1;

import cn.hutool.core.lang.Dict;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.skyer.afterSales.app.service.InstallOrderService;
import org.skyer.afterSales.config.AfterSalesSwaggerApiConfig;
import org.skyer.afterSales.domain.dto.InstallOrderDTO;
import org.skyer.afterSales.domain.dto.MultiCodeDelDTO;
import org.skyer.afterSales.domain.dto.PageInstallOrderDTO;
import org.skyer.afterSales.domain.entity.LogisticsCompany;
import org.skyer.core.base.BaseController;
import org.skyer.core.domain.Page;
import org.skyer.core.iam.ResourceLevel;
import org.skyer.core.util.Results;
import org.skyer.mybatis.pagehelper.annotation.SortDefault;
import org.skyer.mybatis.pagehelper.domain.PageRequest;
import org.skyer.mybatis.pagehelper.domain.Sort;
import org.skyer.swagger.annotation.Permission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 安装工单表 管理 API
 *
 * @author chenzz 2021-09-13 14:46:41
 */
@RestController("installOrderSiteController.v1")
@RequestMapping("/v1/install-orders")
@Api(tags = AfterSalesSwaggerApiConfig.INSTALL_ORDERS)
public class InstallOrderController extends BaseController {

    @Autowired
    private InstallOrderService installOrderService;

    @ApiOperation(value = "物流商列表分页")
    @Permission(level = ResourceLevel.SITE)
    @GetMapping
    public ResponseEntity<Page<?>> list(PageInstallOrderDTO queryDto, @ApiIgnore @SortDefault(value = LogisticsCompany.FIELD_ID,
            direction = Sort.Direction.DESC) PageRequest pageRequest) {
        Page<Dict> list = installOrderService.page(pageRequest, queryDto);
        return Results.success(list);
    }

    @ApiOperation(value = "物流商明细")
    @Permission(level = ResourceLevel.SITE)
    @GetMapping("/{code}")
    public ResponseEntity<?> detail(@PathVariable String code) {
        Dict detail = installOrderService.detail(code);
        return Results.success(detail);
    }

    @ApiOperation(value = "创建物流商")
    @Permission(level = ResourceLevel.SITE)
    @PostMapping
    public ResponseEntity<?> create(@RequestBody InstallOrderDTO dto) {
        validObject(dto);
        boolean result = installOrderService.create(dto);
        return result ? Results.success("添加成功.") : Results.error("添加失败!");
    }

    @ApiOperation(value = "修改物流商")
    @Permission(level = ResourceLevel.SITE)
    @PutMapping
    public ResponseEntity<?> update(@RequestBody InstallOrderDTO dto) {
        // validObject(dto);
        boolean result = installOrderService.update(dto);
        return result ? Results.success("修改成功.") : Results.error("修改失败!");
    }

    @ApiOperation(value = "删除物流商(支持1个和多个)")
    @Permission(level = ResourceLevel.SITE)
    @DeleteMapping
    public ResponseEntity<?> batchRemove(@ApiParam("主键ID") @RequestBody MultiCodeDelDTO delDto) {
        int result = installOrderService.batchRemove(delDto.getCodes());
        return result > 0 ? Results.success(result + "条数据被删除.") : Results.error("删除出错!");
    }

}
