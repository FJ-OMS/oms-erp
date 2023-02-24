package org.skyer.goods.api.controller.v1;

import cn.hutool.core.util.ObjectUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.skyer.boot.platform.lov.constant.LovConstants;
import org.skyer.boot.platform.lov.dto.LovDTO;
import org.skyer.core.base.BaseController;
import org.skyer.core.domain.Page;
import org.skyer.core.domain.PageInfo;
import org.skyer.core.exception.CommonException;
import org.skyer.core.iam.ResourceLevel;
import org.skyer.core.oauth.CustomUserDetails;
import org.skyer.core.oauth.DetailsHelper;
import org.skyer.core.util.Results;
import org.skyer.goods.api.dto.UpdatePriceItemValueDTO;
import org.skyer.goods.config.GoodsSwaggerApiConfig;
import org.skyer.goods.infra.constant.Constants;
import org.skyer.goods.infra.feign.LovManageRemoteService;
import org.skyer.goods.infra.feign.LovRemoteService;
import org.skyer.goods.infra.feign.dto.LovHeaderDto;
import org.skyer.goods.infra.feign.dto.LovValueDto;
import org.skyer.mybatis.pagehelper.domain.PageRequest;
import org.skyer.swagger.annotation.Permission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 租户价格项 设置 API
 *
 * @author chenzz
 * @date 2021-07-06
 */
@RestController("priceItemSettingController.v1")
@RequestMapping("/v1/price/item/setting")
@Api(tags = GoodsSwaggerApiConfig.GOODS_PRICE_SETTING)
public class PriceItemSettingController extends BaseController {

    @Autowired
    LovRemoteService lovRemoteService;

    @Autowired
    LovManageRemoteService lovManageRemoteService;

    /**
     * 租户价格项值集查询 PRICE.ITEM.TYPE
     **/
    @ApiOperation(value = "租户价格项值集查询(分页)")
    @Permission(level = ResourceLevel.ORGANIZATION, permissionLogin = true)
    @GetMapping("/page")
    public ResponseEntity<Page<LovValueDto>> pagePriceItemSetting(PageRequest pageRequest) {
        CustomUserDetails userDetails = DetailsHelper.getUserDetails();
        Long tenantId = userDetails.getTenantId();

        ResponseEntity<List<LovValueDto>> mapResponseEntity = lovRemoteService.queryAllLovValue(Constants.HPFM_LOV_VALUES_PRICE_ITEM, tenantId, null, tenantId);
        List<LovValueDto> body = mapResponseEntity.getBody();

        int pageSize = pageRequest.getSize();
        int pageIndex = pageRequest.getPage() + 1;
        List<LovValueDto> pageList = body.stream().skip((pageIndex) * pageSize).limit(pageSize).collect(Collectors.toList());

        int totalPage = (body.size() + pageSize - 1) / pageSize;
        Page<LovValueDto> pageVOS = new Page<>(pageList, new PageInfo(totalPage, pageSize), body.size());
        return Results.success(pageVOS);
    }

    /**
     * 租户价格项值集查询 PRICE.ITEM.TYPE
     **/
    @ApiOperation(value = "租户价格项值集查询(所有数据列表)")
    @Permission(level = ResourceLevel.ORGANIZATION, permissionLogin = true)
    @GetMapping
    public ResponseEntity<List<LovValueDto>> getPriceItemSetting() {
        CustomUserDetails userDetails = DetailsHelper.getUserDetails();
        Long tenantId = userDetails.getTenantId();

        ResponseEntity<List<LovValueDto>> mapResponseEntity = lovRemoteService.queryAllLovValue(Constants.HPFM_LOV_VALUES_PRICE_ITEM, tenantId, null, tenantId);
        List<LovValueDto> body = mapResponseEntity.getBody();
        return Results.success(body);
    }

    @ApiOperation(value = "值集头创建")
    @Permission(level = ResourceLevel.ORGANIZATION, permissionLogin = true)
    @PostMapping("/add-head")
    public ResponseEntity<LovDTO> saveHeadPriceItemSetting(@ApiParam("值集名称") @RequestParam String lovName) {
        CustomUserDetails userDetails = DetailsHelper.getUserDetails();
        Long tenantId = userDetails.getTenantId();
        ResponseEntity<LovDTO> lovDTOResponseEntity = lovManageRemoteService.queryHeaderByCode(tenantId, Constants.HPFM_LOV_VALUES_PRICE_ITEM);
        if (HttpStatus.OK.equals(lovDTOResponseEntity.getStatusCode())) {
            throw new CommonException("该值集头已经存在");
        }
        LovHeaderDto lovDTO = new LovHeaderDto();
        lovDTO.setTenantId(tenantId);
        lovDTO.setLovCode(Constants.HPFM_LOV_VALUES_PRICE_ITEM);
        lovDTO.setLovName(lovName);
        lovDTO.setMustPageFlag(1);
        lovDTO.setEnabledFlag(1);
        lovDTO.setLovTypeCode(LovConstants.LovTypes.IDP);
        return lovManageRemoteService.addLovHeader(tenantId, lovDTO);
    }

    @ApiOperation(value = "值集value创建")
    @Permission(level = ResourceLevel.ORGANIZATION, permissionLogin = true)
    @PostMapping("/addValue")
    public ResponseEntity<LovValueDto> saveValuePriceItemSetting(@RequestBody UpdatePriceItemValueDTO dto) {
        CustomUserDetails userDetails = DetailsHelper.getUserDetails();
        Long tenantId = userDetails.getTenantId();
        String lovCode = Constants.HPFM_LOV_VALUES_PRICE_ITEM;
        ResponseEntity<LovDTO> lovDTOResponseEntity = lovManageRemoteService.queryHeaderByCode(tenantId, lovCode);
        if (HttpStatus.OK.equals(lovDTOResponseEntity.getStatusCode())) {
            LovDTO lov = lovDTOResponseEntity.getBody();
            if (null != lov) {
                LovValueDto lovValueDto = new LovValueDto();
                lovValueDto.setLovId(lov.getLovId());
                lovValueDto.setLovCode(lovCode);
                lovValueDto.setValue(dto.getValue());
                lovValueDto.setMeaning(dto.getMeaning());
                lovValueDto.setDescription(dto.getDescription());
                // 设置启用，禁用标志
                if (ObjectUtil.isNotNull(dto.getEnabledFlag())) {
                    lovValueDto.setEnabledFlag(dto.getEnabledFlag());
                } else {
                    lovValueDto.setEnabledFlag(1);
                }
                lovValueDto.setOrderSeq(dto.getOrderSeq());
                lovValueDto.setTenantId(tenantId);
                return lovManageRemoteService.addLovValues(tenantId, lovValueDto);
            } else {
                throw new CommonException("值集头不存在");
            }
        } else {
            throw new CommonException("值集头不存在");
        }
    }

    @ApiOperation(value = "值集value修改")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @PutMapping("/value-update")
    public ResponseEntity<LovValueDto> updatePriceItemValueSettings(@RequestBody UpdatePriceItemValueDTO dto) {
        // 查询租户信息
        CustomUserDetails userDetails = DetailsHelper.getUserDetails();
        Long tenantId = userDetails.getTenantId();
        String lovCode = Constants.HPFM_LOV_VALUES_PRICE_ITEM;
        // 根据租户和lovCode获取值集头信息
        ResponseEntity<LovDTO> lovDTOResponseEntity = lovManageRemoteService.queryHeaderByCode(tenantId, lovCode);
        LovDTO lov = lovDTOResponseEntity.getBody();
        if (HttpStatus.OK.equals(lovDTOResponseEntity.getStatusCode()) && null != lov) {
            // 查询租户下loveCode, value匹配的值集
            ResponseEntity<List<LovValueDto>> listResponseEntity = lovManageRemoteService.listValuesByLovId(tenantId, lov.getLovId(), dto.getValue(), null);
            List<LovValueDto> body = listResponseEntity.getBody();
            if (HttpStatus.OK.equals(listResponseEntity.getStatusCode()) && !CollectionUtils.isEmpty(body)) {
                LovValueDto lovValueDto = body.get(0);
                lovValueDto.setMeaning(dto.getMeaning());
                lovValueDto.setDescription(dto.getDescription());
                // 设置可用、禁用标志
                if (ObjectUtil.isNotNull(dto.getEnabledFlag())) {
                    lovValueDto.setEnabledFlag(dto.getEnabledFlag());
                }
                lovValueDto.setOrderSeq(dto.getOrderSeq());
                return lovManageRemoteService.updateLovValuesByPrimaryKey(tenantId, lovValueDto);
            } else {
                throw new CommonException("值集值不存在");
            }
        } else {
            throw new CommonException("值集头不存在");
        }
    }

    @ApiOperation(value = "值集value删除")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @DeleteMapping("/value-delete/{valueCode}")
    public ResponseEntity deletePriceItemValueSettings(@PathVariable("valueCode") String valueCode) {
        CustomUserDetails userDetails = DetailsHelper.getUserDetails();
        Long tenantId = userDetails.getTenantId();
        String lovCode = Constants.HPFM_LOV_VALUES_PRICE_ITEM;
        ResponseEntity<LovDTO> lovDTOResponseEntity = lovManageRemoteService.queryHeaderByCode(tenantId, lovCode);
        LovDTO lov = lovDTOResponseEntity.getBody();
        if (HttpStatus.OK.equals(lovDTOResponseEntity.getStatusCode()) && null != lov) {
            ResponseEntity<List<LovValueDto>> listResponseEntity = lovManageRemoteService.listValuesByLovId(tenantId, lov.getLovId(), valueCode, null);
            List<LovValueDto> body = listResponseEntity.getBody();
            if (HttpStatus.OK.equals(listResponseEntity.getStatusCode()) && !CollectionUtils.isEmpty(body)) {
                LovValueDto lovValueDto = body.get(0);
                List<LovValueDto> lovValueDtos = new ArrayList<>();
                lovValueDtos.add(lovValueDto);
                return lovManageRemoteService.batchDeleteLovValuesByPrimaryKey(tenantId, lovValueDtos);
            } else {
                throw new CommonException("值集值不存在");
            }
        } else {
            throw new CommonException("值集头不存在");
        }
    }

}
