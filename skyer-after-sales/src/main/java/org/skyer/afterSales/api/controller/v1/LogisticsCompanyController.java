package org.skyer.afterSales.api.controller.v1;

import cn.hutool.core.lang.Dict;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.skyer.afterSales.app.service.LogisticsCompanyService;
import org.skyer.afterSales.domain.dto.LogisticsCompanyDTO;
import org.skyer.afterSales.domain.dto.MultiDelDTO;
import org.skyer.afterSales.domain.dto.PageLogisticsCompanyDTO;
import org.skyer.afterSales.domain.entity.LogisticsCompany;
import org.skyer.core.base.BaseController;
import org.skyer.core.domain.Page;
import org.skyer.core.iam.ResourceLevel;
import org.skyer.core.util.Results;
import org.skyer.mybatis.pagehelper.annotation.SortDefault;
import org.skyer.mybatis.pagehelper.domain.PageRequest;
import org.skyer.mybatis.pagehelper.domain.Sort;
import org.skyer.starter.keyencrypt.core.Encrypt;
import org.skyer.swagger.annotation.Permission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 物流商表 管理 API
 *
 * @author chenzz 2021-09-13 10:39:10
 */
@RestController("logisticsCompanySiteController.v1")
@RequestMapping("/xxx/logistics-companys")
@Api(tags = "LogisticsCompanyController")
@Deprecated
public class LogisticsCompanyController extends BaseController {

    @Autowired
    private LogisticsCompanyService logisticsCompanyService;

    @ApiOperation(value = "物流商列表分页")
    @Permission(level = ResourceLevel.SITE)
    @GetMapping
    public ResponseEntity<Page<?>> list(PageLogisticsCompanyDTO logisticsCompanyDTO, @ApiIgnore @SortDefault(value = LogisticsCompany.FIELD_ID,
            direction = Sort.Direction.DESC) PageRequest pageRequest) {
        Page<Dict> list = logisticsCompanyService.page(pageRequest, logisticsCompanyDTO);
        return Results.success(list);
    }

    @ApiOperation(value = "物流商明细")
    @Permission(level = ResourceLevel.SITE)
    @GetMapping("/{id}")
    public ResponseEntity<?> detail(@Encrypt @PathVariable Long id) {
        Dict detail = logisticsCompanyService.detail(id);
        return Results.success(detail);
    }

    @ApiOperation(value = "创建物流商")
    @Permission(level = ResourceLevel.SITE)
    @PostMapping
    public ResponseEntity<?> create(@RequestBody LogisticsCompanyDTO dto) {
        validObject(dto);
        boolean result = logisticsCompanyService.create(dto);
        return result ? Results.success("添加成功.") : Results.error("添加失败!");
    }

    @ApiOperation(value = "修改物流商")
    @Permission(level = ResourceLevel.SITE)
    @PutMapping
    public ResponseEntity<?> update(@RequestBody LogisticsCompanyDTO dto) {
        // validObject(dto);
        boolean result = logisticsCompanyService.update(dto);
        return result ? Results.success("修改成功.") : Results.error("修改失败!");
    }

    @ApiOperation(value = "删除物流商(支持1个和多个)")
    @Permission(level = ResourceLevel.SITE)
    @DeleteMapping
    public ResponseEntity<?> batchRemove(@ApiParam("主键ID") @RequestBody MultiDelDTO idsReqDTO) {
        int result = logisticsCompanyService.batchRemove(idsReqDTO.getIds());
        return result > 0 ? Results.success(result + "条数据被删除.") : Results.error("删除出错!");
    }

}
