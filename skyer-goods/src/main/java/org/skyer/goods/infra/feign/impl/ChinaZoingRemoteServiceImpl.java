package org.skyer.goods.infra.feign.impl;

import org.skyer.goods.domain.vo.ChinaZoingRemoteVO;
import org.skyer.goods.infra.feign.ChinaZoingRemoteService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class ChinaZoingRemoteServiceImpl implements ChinaZoingRemoteService {

    @Override
    public ResponseEntity<ChinaZoingRemoteVO> showDetails(Long areaCode) {
        return null;
    }
}
