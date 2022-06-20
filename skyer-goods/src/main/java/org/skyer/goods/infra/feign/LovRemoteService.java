package org.skyer.goods.infra.feign;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.skyer.boot.platform.lov.annotation.LovValue;
import org.skyer.boot.platform.lov.dto.LovDTO;
import org.skyer.boot.platform.lov.dto.LovValueDTO;
import org.skyer.common.SkyerService;
import org.skyer.goods.infra.feign.dto.LovValueDto;
import org.skyer.goods.infra.feign.impl.LovRemoteServiceImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Primary;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * 值集API 远程 Feign 调用
 *
 * @author chenzz
 */
@Primary
@FeignClient(value = SkyerService.Platform.NAME, fallback = LovRemoteServiceImpl.class, path = "/v1")
public interface LovRemoteService {


    /**
     * 获取值集信息
     *
     * @param lovCode
     * @param organizationId
     * @param tenantId
     * @return
     */
    @GetMapping("/{organizationId}/lovs/info")
    ResponseEntity<LovDTO> queryLovInfo(
            @RequestParam String lovCode,
            @PathVariable("organizationId") Long organizationId,
            @RequestParam(required = false) Long tenantId);


    /**
     * 集成获取值集数据
     *
     * @param lovCode
     * @param organizationId
     * @param tag
     * @param page
     * @param size
     * @param params
     * @param tenantId
     * @return
     */
    @GetMapping("/{organizationId}/lovs/data")
    ResponseEntity<List<Map<String, Object>>> queryLovData(
            @RequestParam String lovCode,
            @PathVariable("organizationId") Long organizationId,
            @RequestParam(required = false) String tag,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size,
            @RequestParam Map<String, String> params,
            @RequestParam(required = false) Long tenantId);

    /**
     * 获取值集SQL
     *
     * @param lovCode
     * @param organizationId
     * @param tenantId
     * @return
     */
    @GetMapping(path = "/{organizationId}/lovs/sql")
    ResponseEntity<String> queryLovSql(
            @RequestParam String lovCode,
            @PathVariable("organizationId") Long organizationId,
            @RequestParam(required = false) Long tenantId);

    /**
     * 获取值集值
     *
     * @param lovCode
     * @param organizationId
     * @param tag
     * @param tenantId
     * @return
     */
    @GetMapping(path = "/{organizationId}/lovs/all-value")
    ResponseEntity<List<LovValueDto>> queryAllLovValue(
            @RequestParam String lovCode,
            @PathVariable("organizationId") Long organizationId,
            @RequestParam(required = false) String tag,
            @RequestParam(required = false) Long tenantId);


    /**
     * 批量获取值集值
     *
     * @param queryMap
     * @param organizationId
     * @param tenantId
     * @return
     */
    @GetMapping(path = "/{organizationId}/lovs/value/batch")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "queryMap", value = "批量查询条件,形式:返回key=code", paramType = "query", example = "codeOne=CODE1&codeTwo=CODE2", required = true),
            @ApiImplicitParam(name = "tenantId", value = "租户ID", paramType = "query")
    })
    ResponseEntity<Map<String, List<LovValueDTO>>> batchQueryLovValue(
            @RequestParam Map<String, String> queryMap,
            @PathVariable("organizationId") Long organizationId,
            @RequestParam(required = false) Long tenantId);


    /**
     * 获取值集信息
     *
     * @param lovCode
     * @param tenantId
     * @return
     */
    @GetMapping("/lovs/info")
    ResponseEntity<LovDTO> queryLovInfoSite(
            @RequestParam String lovCode,
            @RequestParam(required = false) Long tenantId);

    /**
     * 集成获取值集数据
     *
     * @param lovCode
     * @param tenantId
     * @param tag
     * @param page
     * @param size
     * @param params
     * @return
     */
    @GetMapping("/lovs/data")
    ResponseEntity<List<Map<String, Object>>> queryLovDataSite(
            @RequestParam String lovCode,
            @RequestParam(required = false) Long tenantId,
            @RequestParam(required = false) String tag,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size,
            @RequestParam Map<String, String> params
    );
}
