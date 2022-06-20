package org.skyer.goods.infra.feign;

import org.skyer.boot.platform.lov.dto.LovDTO;
import org.skyer.boot.platform.lov.dto.LovValueDTO;
import org.skyer.common.SkyerService;
import org.skyer.goods.infra.feign.dto.LovHeaderDto;
import org.skyer.goods.infra.feign.dto.LovValueDto;
import org.skyer.goods.infra.feign.impl.LovManageRemoteServiceImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Primary;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 值集管理API(租户级) 远程 Feign 调用
 *
 * @author chenzz
 */
@Primary
@FeignClient(value = SkyerService.Platform.NAME, fallback = LovManageRemoteServiceImpl.class, path = "/v1")
public interface LovManageRemoteService  {

    @GetMapping("/{organizationId}/lov-headers-code/{lovCode}")
    ResponseEntity<LovDTO> queryHeaderByCode(
            @PathVariable("organizationId") Long tenantId,
            @PathVariable  String lovCode);

    /**
     * 插入值集头
     * @param tenantId
     * @param lovHeader
     * @return
     */
    @PostMapping("/{organizationId}/lov-headers")
    ResponseEntity<LovDTO> addLovHeader(
            @PathVariable("organizationId") Long tenantId,
            @RequestBody LovHeaderDto lovHeader);

    /**
     * 插入值集值
     * @param tenantId
     * @param lovValue
     * @return
     */
    @PostMapping("/{organizationId}/lov-values")
    ResponseEntity<LovValueDto> addLovValues(
            @PathVariable("organizationId") Long tenantId,
            @RequestBody LovValueDto lovValue);

    /**
     * 更新值集头
     * @param tenantId
     * @param lovHeader
     * @return
     */
    @PutMapping("/{organizationId}/lov-headers")
    ResponseEntity<LovDTO> updateLovHeadersByPrimaryKey(
            @PathVariable("organizationId") Long tenantId,
            @RequestBody LovDTO lovHeader);

    /**
     * 根据值集头ID查询值集值
     * @param tenantId
     * @param lovId
     * @param value
     * @param meaning
     * @return
     */
    @GetMapping("/{organizationId}/lov-headers/{lovId}/values-list")
    ResponseEntity<List<LovValueDto>> listValuesByLovId(
            @PathVariable("organizationId") Long tenantId,
            @PathVariable Long lovId,
            @RequestParam(value = "value", required = false) String value,
            @RequestParam(value = "meaning", required = false) String meaning);

    /**
     * 更新值集
     * @param tenantId
     * @param lovValue
     * @return
     */
    @PutMapping("/{organizationId}/lov-values")
    ResponseEntity<LovValueDto> updateLovValuesByPrimaryKey(
           @PathVariable("organizationId") Long tenantId,
            @RequestBody LovValueDto lovValue);

    /**
     * 批量删除值集头
     * @param tenantId
     * @param lovHeaders
     * @return
     */
    @DeleteMapping("/{organizationId}/lov-headers")
    ResponseEntity batchDeleteLovHeadersByPrimaryKey(
            @PathVariable("organizationId") Long tenantId,
            @RequestBody List<LovDTO> lovHeaders);

    /**
     * 批量删除值集
     * @param tenantId
     * @param lovValues
     * @return
     */
    @DeleteMapping("/{organizationId}/lov-values")
    ResponseEntity batchDeleteLovValuesByPrimaryKey(
            @PathVariable("organizationId") Long tenantId,
            @RequestBody List<LovValueDto> lovValues);
    
}
