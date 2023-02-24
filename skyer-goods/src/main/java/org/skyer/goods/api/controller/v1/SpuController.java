package org.skyer.goods.api.controller.v1;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

import org.skyer.core.domain.Page;
import org.skyer.core.iam.ResourceLevel;
import org.skyer.core.util.Results;
import org.skyer.goods.api.dto.GoodsPageReqDTO;
import org.skyer.goods.api.dto.GoodsRequestDTO;
import org.skyer.goods.api.dto.IdsReqDTO;
import org.skyer.goods.api.dto.StatusReqDTO;
import org.skyer.goods.app.service.SpuEsService;
import org.skyer.goods.config.GoodsSwaggerApiConfig;
import org.skyer.goods.domain.service.SpuService;
import org.skyer.goods.domain.vo.GoodsDetailsVO;
import org.skyer.goods.domain.vo.GoodsPageResVO;
import org.skyer.starter.keyencrypt.core.Encrypt;
import org.skyer.swagger.annotation.Permission;

/**
 * 商品管理
 *
 * @description
 * @Author wusc
 * @create 2021/6/24 10:36 上午
 */
@RestController("GoodsController.v1")
@RequestMapping(value = "/v1/goods/manager")
@Api(tags = GoodsSwaggerApiConfig.GOODS_MANAGER)
public class SpuController {

    @Autowired
    private SpuService spuService;
    @Autowired
    private SpuEsService spuEsService;

    @ApiOperation(value = "商品页面查询")
    @PostMapping("/page")
    @Permission(level = ResourceLevel.ORGANIZATION, permissionLogin = true)
    public ResponseEntity<Page<GoodsPageResVO>> pageGoods(@Valid @RequestBody GoodsPageReqDTO goodsPageReqDTO) throws Exception {
        if (StringUtils.isNotBlank(goodsPageReqDTO.getName())) {
            return Results.success(spuEsService.querySpuEs(goodsPageReqDTO));
        }
        return Results.success(spuService.pageGoods(goodsPageReqDTO));
    }

    @ApiOperation(value = "查询spu的商品List")
    @PostMapping("/goods-list-spu")
    @Permission(level = ResourceLevel.ORGANIZATION, permissionLogin = true)
    public ResponseEntity<List<GoodsPageResVO>> queryGoodsBySpuList(@RequestBody List<String> spuCodeList) {
        return Results.success(spuService.queryGoodsBySpuList(spuCodeList));
    }


    @ApiOperation(value = "更新商品")
    @PutMapping("/goods")
    @Permission(level = ResourceLevel.ORGANIZATION, permissionLogin = true)
    public ResponseEntity<String> updateGoods(@Valid @RequestBody GoodsRequestDTO goodsRequestDTO) {
        spuService.insertOrUpdateGoods(goodsRequestDTO);
        return Results.success("操作成功");
    }

    @ApiOperation("创建商品")
    @PostMapping("/goods")
    @Permission(level = ResourceLevel.ORGANIZATION, permissionLogin = true)
    public ResponseEntity<String> createGoods(@Valid @RequestBody GoodsRequestDTO goodsRequestDTO) {
        spuService.insertOrUpdateGoods(goodsRequestDTO);
        return Results.success("操作成功");
    }

    @ApiOperation(value = "删除商品")
    @PatchMapping("/batch-delete-goods")
    @Permission(level = ResourceLevel.ORGANIZATION, permissionLogin = true)
    public ResponseEntity<String> deleteByIds(@RequestBody IdsReqDTO idsReqDTO) {
        spuService.deleteByIds(idsReqDTO.getIds());
        return Results.success("操作成功");
    }

    @ApiOperation(value = "查询商品详情")
    @GetMapping("/detail/{id}")
    @Permission(level = ResourceLevel.ORGANIZATION, permissionLogin = true)
    public ResponseEntity<GoodsDetailsVO> queryDetail(@ApiParam("主键ID") @Encrypt @PathVariable("id") Long id) {
        GoodsDetailsVO goodsDetailsVO = spuService.getDetailsById(id);
        return Results.success(goodsDetailsVO);
    }

    @ApiOperation(value = "修改商品状态")
    @PatchMapping("/goods")
    @Permission(level = ResourceLevel.ORGANIZATION, permissionLogin = true)
    public ResponseEntity<String> updateGoodsStatus(@Valid @RequestBody StatusReqDTO statusReqDTO) {
        spuService.updateGoodsStatus(statusReqDTO.getIds(), statusReqDTO.getStatus());
        return Results.success("操作成功");
    }

    @ApiOperation(value = "商品批量上传ES测试")
    @GetMapping("/es-test")
    @Permission(level = ResourceLevel.ORGANIZATION, permissionPublic = true)
    public ResponseEntity<Void> esTest() throws IOException {
        spuEsService.productStatusUp();
        return Results.success();
    }

}
