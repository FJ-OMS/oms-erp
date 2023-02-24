package org.skyer.goods.infra.feign.impl;

import org.skyer.boot.platform.lov.dto.LovDTO;
import org.skyer.boot.platform.lov.dto.LovValueDTO;
import org.skyer.goods.infra.feign.LovRemoteService;
import org.skyer.goods.infra.feign.dto.LovValueDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @author chenzz
 */
@Component
public class LovRemoteServiceImpl implements LovRemoteService {
    @Override
    public ResponseEntity<LovDTO> queryLovInfo(String lovCode, Long organizationId, Long tenantId) {
        return null;
    }

    @Override
    public ResponseEntity<List<Map<String, Object>>> queryLovData(String lovCode, Long organizationId, String tag, Integer page, Integer size, Map<String, String> params, Long tenantId) {
        return null;
    }

    @Override
    public ResponseEntity<String> queryLovSql(String lovCode, Long organizationId, Long tenantId) {
        return null;
    }

    @Override
    public ResponseEntity<List<LovValueDto>> queryAllLovValue(String lovCode, Long organizationId, String tag, Long tenantId) {
        return null;
    }

    @Override
    public ResponseEntity<Map<String, List<LovValueDTO>>> batchQueryLovValue(Map<String, String> queryMap, Long organizationId, Long tenantId) {
        return null;
    }

    @Override
    public ResponseEntity<LovDTO> queryLovInfoSite(String lovCode, Long tenantId) {
        return null;
    }

    @Override
    public ResponseEntity<List<Map<String, Object>>> queryLovDataSite(String lovCode, Long tenantId, String tag, Integer page, Integer size, Map<String, String> params) {
        return null;
    }
}
