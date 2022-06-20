package org.skyer.goods.infra.feign;

import org.skyer.common.SkyerService;
import org.skyer.goods.domain.vo.ChinaZoingRemoteVO;
import org.skyer.goods.infra.feign.impl.ChinaZoingRemoteServiceImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @description
 * @Author wusc
 * @create 2021/8/16 3:57 下午
 */
@FeignClient(value = SkyerService.Platform.NAME, fallback = ChinaZoingRemoteServiceImpl.class, path = "/v1/cnareaAdministrativeDivision")
public interface ChinaZoingRemoteService {


    /**
     * 根据Id获取中国行政地区表信息
     * <p>
     * curl -X GET --header 'Accept: text/html' 'http://api.skyer.org/spfm/v1/cnareaAdministrativeDivision/details?areaCode=sss'
     * <p>
     * {
     * "levelThreeAreaCode": 110101001008,
     * "levelTwoAreaCode": 110101001000,
     * "levelOneAreaCode": 110101000000,
     * "levelThreeAreaName": "黄图岗社区居委会",
     * "levelTwoAreaName": "东华门街道",
     * "levelOneAreaName": "东城区"
     * }
     *
     * @param
     * @return
     */
    @GetMapping("/details")
    ResponseEntity<ChinaZoingRemoteVO> showDetails(@RequestParam(value = "areaCode") Long areaCode);
}
