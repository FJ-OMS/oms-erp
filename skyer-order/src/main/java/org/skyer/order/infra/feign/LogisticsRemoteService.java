package org.skyer.order.infra.feign;

import org.skyer.order.infra.feign.entity.select.logistics.postage.in.CalcPostageChargeDTO;
import org.skyer.order.infra.feign.entity.select.logistics.postage.out.PackageSkuLogistics;
import org.skyer.order.infra.feign.impl.LogisticsRemoteServiceImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @author chenzz
 */
@FeignClient(value = "skyer-logistics", fallback = LogisticsRemoteServiceImpl.class, path = "/v1")
public interface LogisticsRemoteService {


    /**
     * /v1/postage-templets/select-logistics-and-calc-postage
     * 根据订单金额等选择物流业务及计算运费
     *
     * @param dto
     * @return
     */
    @PostMapping("/postage-templets/select-logistics-and-calc-postage")
    ResponseEntity<List<PackageSkuLogistics>> selectLogisticsAndCalcPostage(@RequestBody CalcPostageChargeDTO dto);

}
