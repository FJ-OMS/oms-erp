package org.skyer.goods.api.controller.v1;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.skyer.core.iam.ResourceLevel;
import org.skyer.core.util.Results;
import org.skyer.goods.app.service.SkuService;
import org.skyer.goods.config.GoodsSwaggerApiConfig;
import org.skyer.goods.domain.dto.AuditDTO;
import org.skyer.goods.domain.dto.SkuAuditDTO;
import org.skyer.goods.domain.vo.SkuAuditVO;
import org.skyer.starter.keyencrypt.core.Encrypt;
import org.skyer.swagger.annotation.Permission;

/**
 * SKU审核管理s
 * @author lixiaoyang
 * @date 2021/12/30 10:58
 */
@RestController("skuAuditController.v1")
@RequestMapping("/v1/sku/audit")
@Api(tags = GoodsSwaggerApiConfig.GOODS_SKU_AUDIT_MANAGER)
public class SkuAuditController {
    @Autowired
    private SkuService skuService;

    @ApiOperation(value = "SKU审核详情")
    @Permission(level = ResourceLevel.ORGANIZATION,permissionLogin = true)
    @PostMapping("/detail")
    public ResponseEntity<SkuAuditVO> detail( @RequestBody SkuAuditDTO dto) {
        return Results.success(skuService.getAuditDetail(dto));
    }

    @ApiOperation(value = "SKU审核操作")
    @Permission(level = ResourceLevel.ORGANIZATION,permissionLogin = true)
    @PostMapping
    public ResponseEntity<Boolean> audit( @RequestBody AuditDTO dto) {
        return Results.success(skuService.skuAudit(dto));
    }
}
