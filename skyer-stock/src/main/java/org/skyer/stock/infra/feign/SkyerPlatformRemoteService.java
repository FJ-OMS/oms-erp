package org.skyer.stock.infra.feign;

import io.swagger.annotations.ApiParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Primary;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.skyer.common.SkyerService;
import org.skyer.stock.domain.vo.AreaInfoDetailsVO;
import org.skyer.stock.infra.feign.impl.SkyerPlatformRemoteServiceImpl;

/**
 * 获取地址信息远程 Feign 调用
 *
 * @author lzh
 */
@Primary
@FeignClient(value = SkyerService.Platform.NAME, fallback = SkyerPlatformRemoteServiceImpl.class, path = "/v1/cnareaAdministrativeDivision")
public interface SkyerPlatformRemoteService {
	/**
	 * 远程调用获取三级地区名称
	 * @param areaCode
	 * @return
	 */
	 @GetMapping("/details")
	 ResponseEntity<AreaInfoDetailsVO> getCnareaAdministrativeDivisionDetail(
	 		@ApiParam("主键ID")
			@RequestParam(value = "areaCode") Long areaCode);
}
