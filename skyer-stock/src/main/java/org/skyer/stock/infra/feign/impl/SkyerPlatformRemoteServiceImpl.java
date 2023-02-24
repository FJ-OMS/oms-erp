package org.skyer.stock.infra.feign.impl;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import org.skyer.stock.domain.vo.AreaInfoDetailsVO;
import org.skyer.stock.infra.feign.SkyerPlatformRemoteService;

/**
 * @author: lzh
 * @date: 2021-08-24
 * @description:
 */
@Component
public class SkyerPlatformRemoteServiceImpl implements SkyerPlatformRemoteService {
	@Override
	public ResponseEntity<AreaInfoDetailsVO> getCnareaAdministrativeDivisionDetail(Long areaCode) {
		return null;
	}
}
