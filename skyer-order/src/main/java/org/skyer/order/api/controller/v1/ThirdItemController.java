package org.skyer.order.api.controller.v1;

import org.skyer.core.base.BaseController;
import org.skyer.order.domain.repository.ThirdItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * 内部订单商品明细 管理 API
 *
 * @author zhanglongbing@ebc.com 2022-04-20 10:15:56
 */
@RestController("thirdItemSiteController.v1")
@RequestMapping("/v1/third-item")
public class ThirdItemController extends BaseController {

    @Autowired
    private ThirdItemRepository thirdItemRepository;



}
