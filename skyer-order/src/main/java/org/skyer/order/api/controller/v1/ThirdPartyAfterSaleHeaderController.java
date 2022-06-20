package org.skyer.order.api.controller.v1;

import org.skyer.core.util.Results;
import org.skyer.core.base.BaseController;
import org.skyer.order.api.dto.OrderDetailSearchDTO;
import org.skyer.order.api.dto.RefundAuditPageDTO;
import org.skyer.order.api.dto.ThirdPartyAfterSaleDTO;
import org.skyer.order.api.dto.ThirdPartyAfterSalePageDTO;
import org.skyer.order.api.vo.RefundDetailsVO;
import org.skyer.order.api.vo.RefundPageVO;
import org.skyer.order.api.vo.ThirdPartyAfterSaleHeaderDetailsVO;
import org.skyer.order.api.vo.ThirdPartyAfterSalePageVO;
import org.skyer.order.domain.entity.ThirdPartyAfterSaleHeader;
import org.skyer.order.domain.repository.ThirdPartyAfterSaleHeaderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.skyer.mybatis.helper.SecurityTokenHelper;

import org.skyer.core.domain.Page;
import org.skyer.core.iam.ResourceLevel;
import org.skyer.mybatis.pagehelper.annotation.SortDefault;
import org.skyer.mybatis.pagehelper.domain.PageRequest;
import org.skyer.mybatis.pagehelper.domain.Sort;
import org.skyer.starter.keyencrypt.core.Encrypt;
import org.skyer.swagger.annotation.Permission;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;

/**
 * 第三方售后单头 管理 API
 *
 * @author lzh 2022-04-20 16:30:30
 */
@RestController("thirdPartyAfterSaleHeaderSiteController.v1")
@RequestMapping("/v1/third-party-after-sale-headers")
public class ThirdPartyAfterSaleHeaderController extends BaseController {

    @Autowired
    private ThirdPartyAfterSaleHeaderRepository thirdPartyAfterSaleHeaderRepository;

    @ApiOperation(value = "第三方售后单头列表")
    @Permission(level = ResourceLevel.SITE)
    @PostMapping("order-page")
    public ResponseEntity<Page<ThirdPartyAfterSalePageVO>> orderPage(@RequestBody ThirdPartyAfterSalePageDTO thirdPartyAfterSalePageDTO) {
        Page<ThirdPartyAfterSalePageVO> pages = thirdPartyAfterSaleHeaderRepository.orderPage(thirdPartyAfterSalePageDTO);
        return Results.success(pages);
    }

    @ApiOperation(value = "第三方售后单明细-退货/退款")
    @Permission(level = ResourceLevel.SITE)
    @GetMapping("/{id}")
    public ResponseEntity<ThirdPartyAfterSaleHeaderDetailsVO> detail(@PathVariable @Encrypt Long id) {
        ThirdPartyAfterSaleHeaderDetailsVO vos = thirdPartyAfterSaleHeaderRepository.detail(id,null);
        return Results.success(vos);
    }

    @ApiOperation(value = "创建第三方售后单头 发起saga")
    @Permission(level = ResourceLevel.SITE)
    @PostMapping("create-thirdParty-refund")
    public ResponseEntity<String> createThirdPartyOnlyRefund(@RequestBody @Valid ThirdPartyAfterSaleDTO thirdPartyAfterSaleDTO) {
        thirdPartyAfterSaleHeaderRepository.createThirdPartyAfterSaleSaga(thirdPartyAfterSaleDTO);
        return Results.success("创建成功");
    }

    @ApiOperation(value = "内部售后单详情订单基本信息通过code和Id")
    @Permission(level = ResourceLevel.SITE  ,permissionLogin = true)
    @PostMapping("/order-base-detail-by-id-code")
    public ResponseEntity<ThirdPartyAfterSaleHeaderDetailsVO> queryOrderBaseDetailByDTO(@RequestBody OrderDetailSearchDTO orderDetailSearchDTO){
        ThirdPartyAfterSaleHeaderDetailsVO refundDetailsVO = thirdPartyAfterSaleHeaderRepository.detail(orderDetailSearchDTO.getOrderId(),orderDetailSearchDTO.getOrderCode());
        return Results.success(refundDetailsVO);
    }
}
