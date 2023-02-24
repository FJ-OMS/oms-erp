package org.skyer.order.api.controller.v1;

import org.skyer.core.util.Results;
import org.skyer.core.base.BaseController;
import org.skyer.order.api.dto.ThirdOrderTableDTO;
import org.skyer.order.api.es.SearchThirdOrderEsParam;
import org.skyer.order.api.vo.ThirdOrderDetailVO;
import org.skyer.order.api.vo.ThirdOrderPageVo;
import org.skyer.order.app.service.ThirdHeaderService;
import org.skyer.order.app.service.ThirdOrderEsService;
import org.skyer.order.domain.entity.ThirdHeader;
import org.skyer.order.domain.repository.ThirdHeaderRepository;
import org.skyer.starter.keyencrypt.core.Encrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.skyer.core.domain.Page;
import org.skyer.core.iam.ResourceLevel;
import org.skyer.swagger.annotation.Permission;
import io.swagger.annotations.ApiOperation;

/**
 * 第三方订单头 管理 API
 *
 * @author zhanglongbing@ebc.com 2022-04-20 10:15:56
 */
@RestController("thirdHeaderSiteController.v1")
@RequestMapping("/v1/third-order")
public class ThirdOrderController extends BaseController {

    @Autowired
    private ThirdHeaderRepository thirdHeaderRepository;
    @Autowired
    private ThirdHeaderService thirdHeaderService;
    @Autowired
    private ThirdOrderEsService thirdOrderEsService;

    @ApiOperation(value = "第三方订单头列表")
    @Permission(level = ResourceLevel.SITE,permissionLogin = true)
    @PostMapping("/page")
    public ResponseEntity<Page<ThirdOrderPageVo>> page(@RequestBody SearchThirdOrderEsParam searchThirdOrderEsParam) throws Exception {
        Page<ThirdOrderPageVo> thirdOrderPageVoPage = thirdOrderEsService.getThirdOrderPage(searchThirdOrderEsParam);
        return Results.success(thirdOrderPageVoPage);
    }

    @ApiOperation(value = "第三方订单头明细")
    @Permission(level = ResourceLevel.SITE,permissionLogin = true)
    @GetMapping("/detail")
    public ResponseEntity<ThirdOrderDetailVO> detail(@Encrypt  Long id,String outerOrderNo) {
        ThirdOrderDetailVO thirdOrderDetailVO = thirdHeaderService.getThirdOrderDetail(id,outerOrderNo);
        return Results.success(thirdOrderDetailVO);
    }


    @ApiOperation(value = "创建第三方订单头")
    @Permission(level = ResourceLevel.SITE,permissionLogin = true)
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody ThirdOrderTableDTO thirdOrderTableDto) {
        thirdHeaderService.createOuterOrderBpmn(thirdOrderTableDto);
        return Results.success("创建成功");
    }




}
