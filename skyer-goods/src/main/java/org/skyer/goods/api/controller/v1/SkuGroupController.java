package org.skyer.goods.api.controller.v1;

import io.swagger.annotations.Api;
import org.skyer.core.domain.Page;
import org.skyer.core.util.Results;
import org.skyer.core.base.BaseController;
import org.skyer.goods.api.dto.GoodsGroupReqDTO;
import org.skyer.goods.api.dto.GroupPageReqDTO;
import org.skyer.goods.api.dto.IdsReqDTO;
import org.skyer.goods.config.GoodsSwaggerApiConfig;
import org.skyer.goods.domain.service.SkuGroupService;
import org.skyer.goods.domain.vo.GroupPageResVO;
import org.skyer.goods.domain.vo.SkuGroupResVO;
import org.skyer.starter.keyencrypt.core.Encrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.skyer.core.iam.ResourceLevel;
import org.skyer.swagger.annotation.Permission;
import io.swagger.annotations.ApiOperation;

/**
 * Sku组合表 管理 API
 *
 * @author wushaochuan 2021-08-13 09:55:04
 */
@Api(tags = GoodsSwaggerApiConfig.GOODS_GROUP)
@RestController("skuGroupController.v1")
@RequestMapping("/v1/sku-groups")
public class SkuGroupController extends BaseController {
    @Autowired
    private SkuGroupService skuGroupService;

    @ApiOperation(value = "创建或者更新sku组合")
    @Permission(level = ResourceLevel.ORGANIZATION, permissionLogin = true)
    @PostMapping("/sku-group")
    public ResponseEntity<String> createOrUpdateSkuGroup(@RequestBody GoodsGroupReqDTO goodsGroupReqDTO){
        skuGroupService.createOrUpdateSkuGroup(goodsGroupReqDTO);
        return Results.success("操作成功");
    }

    @ApiOperation(value = "sku组合查询")
    @Permission(level = ResourceLevel.ORGANIZATION,permissionLogin = true)
    @GetMapping("/detail/{groupId}")
    public ResponseEntity<SkuGroupResVO> skuGroupDetail(@Encrypt @PathVariable("groupId") Long groupId) {
        SkuGroupResVO skuGroupResVO = skuGroupService.skuGroupDetail(groupId);
        return Results.success(skuGroupResVO);
    }

    @ApiOperation(value = "商品组合分页")
    @Permission(level = ResourceLevel.ORGANIZATION,permissionLogin = true)
    @PostMapping("/page")
    public ResponseEntity<Page<GroupPageResVO>> pageGroup(@RequestBody GroupPageReqDTO groupPageReqDTO) {
        return Results.success(skuGroupService.pageGroup(groupPageReqDTO));
    }

    @ApiOperation(value = "删除商品组合")
    @Permission(level = ResourceLevel.ORGANIZATION,permissionLogin = true)
    @DeleteMapping("/group")
    public ResponseEntity<String> delGroup(@RequestBody IdsReqDTO idsReqDTO) {
        skuGroupService.delGroup(idsReqDTO);
        return Results.success("操作成功");
    }


}
