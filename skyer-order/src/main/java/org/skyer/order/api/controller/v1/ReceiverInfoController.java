package org.skyer.order.api.controller.v1;

import org.skyer.core.util.Results;
import org.skyer.core.base.BaseController;
import org.skyer.order.domain.entity.ReceiverInfo;
import org.skyer.order.domain.repository.ReceiverInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.skyer.mybatis.helper.SecurityTokenHelper;

import org.skyer.core.domain.Page;
import org.skyer.core.iam.ResourceLevel;
import org.skyer.mybatis.pagehelper.annotation.SortDefault;
import org.skyer.mybatis.pagehelper.domain.PageRequest;
import org.skyer.mybatis.pagehelper.domain.Sort;
import org.skyer.swagger.annotation.Permission;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 内部订单收货人信息 管理 API
 *
 * @author wushaochuan 2022-01-18 14:34:53
 */
@RestController("receiverInfoSiteController.v1")
@RequestMapping("/v1/receiver-infos")
public class ReceiverInfoController extends BaseController {

    @Autowired
    private ReceiverInfoRepository receiverInfoRepository;

    @ApiOperation(value = "内部订单收货人信息列表")
    @Permission(level = ResourceLevel.SITE)
    @GetMapping
    public ResponseEntity<Page<ReceiverInfo>> list(ReceiverInfo receiverInfo, @ApiIgnore @SortDefault(value = ReceiverInfo.FIELD_ID,
            direction = Sort.Direction.DESC) PageRequest pageRequest) {
        Page<ReceiverInfo> list = receiverInfoRepository.pageAndSort(pageRequest, receiverInfo);
        return Results.success(list);
    }

    @ApiOperation(value = "内部订单收货人信息明细")
    @Permission(level = ResourceLevel.SITE)
    @GetMapping("/{id}")
    public ResponseEntity<ReceiverInfo> detail(@PathVariable Long id) {
        ReceiverInfo receiverInfo = receiverInfoRepository.selectByPrimaryKey(id);
        return Results.success(receiverInfo);
    }

    @ApiOperation(value = "创建内部订单收货人信息")
    @Permission(level = ResourceLevel.SITE)
    @PostMapping
    public ResponseEntity<ReceiverInfo> create(@RequestBody ReceiverInfo receiverInfo) {
        validObject(receiverInfo);
        receiverInfoRepository.insertSelective(receiverInfo);
        return Results.success(receiverInfo);
    }

    @ApiOperation(value = "修改内部订单收货人信息")
    @Permission(level = ResourceLevel.SITE)
    @PutMapping
    public ResponseEntity<ReceiverInfo> update(@RequestBody ReceiverInfo receiverInfo) {
        SecurityTokenHelper.validToken(receiverInfo);
        receiverInfoRepository.updateByPrimaryKeySelective(receiverInfo);
        return Results.success(receiverInfo);
    }

    @ApiOperation(value = "删除内部订单收货人信息")
    @Permission(level = ResourceLevel.SITE)
    @DeleteMapping
    public ResponseEntity<?> remove(@RequestBody ReceiverInfo receiverInfo) {
        SecurityTokenHelper.validToken(receiverInfo);
        receiverInfoRepository.deleteByPrimaryKey(receiverInfo);
        return Results.success();
    }

}
