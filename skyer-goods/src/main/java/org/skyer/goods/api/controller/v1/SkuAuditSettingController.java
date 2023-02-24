package org.skyer.goods.api.controller.v1;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.skyer.core.base.BaseController;
import org.skyer.core.iam.ResourceLevel;
import org.skyer.core.util.Results;
import org.skyer.goods.app.service.SkuAuditSettingService;
import org.skyer.goods.config.GoodsSwaggerApiConfig;
import org.skyer.goods.domain.entity.SkuAuditSetting;
import org.skyer.goods.domain.repository.SkuAuditSettingRepository;
import org.skyer.swagger.annotation.Permission;

/**
 * 商品SKU审核 管理 API
 *
 * @author zhanglongbing@ebc.com 2021-12-27 10:09:56
 */
@RestController("skuAuditSettingSiteController.v1")
@RequestMapping("/v1/sku-audit-settings")
@Api(tags = GoodsSwaggerApiConfig.GOODS_SKU_AUDIT_SETTING)
public class SkuAuditSettingController extends BaseController {

    @Autowired
    private SkuAuditSettingRepository skuAuditSettingRepository;
    @Autowired
    private SkuAuditSettingService auditSettingService;

    @ApiOperation(value = "商品SKU审核设置明细")
    @Permission(level = ResourceLevel.SITE)
    @GetMapping("/default")
    public ResponseEntity<SkuAuditSetting> detail() {
        SkuAuditSetting skuAuditSetting = skuAuditSettingRepository.selectDefault();
        return Results.success(skuAuditSetting);
    }

    @ApiOperation(value = "修改商品SKU审核设置")
    @Permission(level = ResourceLevel.SITE)
    @PutMapping("/update")
    public ResponseEntity<SkuAuditSetting> update(@RequestBody SkuAuditSetting skuAuditSetting) {
        skuAuditSettingRepository.updateDefault(skuAuditSetting);
        return Results.success(skuAuditSetting);
    }



}
