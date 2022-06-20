package org.skyer.order.api.controller.v1;

import org.skyer.core.base.BaseController;
import org.skyer.order.domain.repository.ThirdReceiverInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * 内部订单收货人信息 管理 API
 *
 * @author zhanglongbing@ebc.com 2022-04-20 10:15:57
 */
@RestController("thirdReceiverInfoSiteController.v1")
@RequestMapping("/v1/third-receiver-info")
public class ThirdReceiverInfoController extends BaseController {

    @Autowired
    private ThirdReceiverInfoRepository thirdReceiverInfoRepository;



}
