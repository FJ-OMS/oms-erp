package org.skyer.goods.app.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Dict;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import org.skyer.core.domain.Page;
import org.skyer.core.domain.PageInfo;
import org.skyer.core.exception.CommonException;
import org.skyer.core.oauth.CustomUserDetails;
import org.skyer.core.oauth.DetailsHelper;
import org.skyer.goods.app.service.InstallServiceService;
import org.skyer.goods.app.service.MyEncryptionService;
import org.skyer.goods.domain.dto.InstallServiceDTO;
import org.skyer.goods.domain.dto.PageInstallServiceDTO;
import org.skyer.goods.domain.dto.ServiceZoneDTO;
import org.skyer.goods.domain.entity.InstallService;
import org.skyer.goods.domain.repository.InstallServiceRepository;
import org.skyer.goods.domain.vo.ChinaZoingRemoteVO;
import org.skyer.goods.infra.feign.ChinaZoingRemoteService;
import org.skyer.mybatis.domian.Condition;
import org.skyer.mybatis.pagehelper.PageHelper;
import org.skyer.mybatis.pagehelper.domain.PageRequest;
import org.skyer.mybatis.util.Sqls;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 安装服务应用服务默认实现
 *
 * @author chenzz 2021-08-18 15:42:47
 */
@Service
public class InstallServiceServiceImpl implements InstallServiceService {

    @Autowired
    private InstallServiceRepository installServiceRepository;

    @Autowired
    private ChinaZoingRemoteService chinaZoingRemoteService;

    @Autowired
    private MyEncryptionService myEncryptionService;

    @Override
    public List<InstallService> list(PageInstallServiceDTO installServiceDTO) {
        // 根据条件查找
        return installServiceRepository.selectByCondition(Condition.builder(InstallService.class)
                .andWhere(Sqls.custom()
                        .andLike(InstallService.FIELD_SERVICE_CODE, installServiceDTO.getServiceCode(), true)
                        .andLike(InstallService.FIELD_NAME, installServiceDTO.getName(), true)
                        .andEqualTo(InstallService.FIELD_DELETE_FLAG, false))
                .build());
    }

    @Override
    public Page<Dict> page(PageRequest pageRequest, PageInstallServiceDTO installServiceDTO) {
        Page<InstallService> page = PageHelper.doPageAndSort(pageRequest, () -> installServiceRepository.selectByCondition(Condition.builder(InstallService.class)
                .andWhere(Sqls.custom()
                        .andLike(InstallService.FIELD_SERVICE_CODE, installServiceDTO.getServiceCode(), true)
                        .andLike(InstallService.FIELD_NAME, installServiceDTO.getName(), true)
                        .andEqualTo(InstallService.FIELD_DELETE_FLAG, false))
                .build()));

        List<InstallService> pageListContent = page.getContent();
        if (CollUtil.isNotEmpty(pageListContent)) {
            List<Dict> dictList = pageListContent.stream().map(xx -> {
                Dict item = Dict.create();

                item.set("id", myEncryptionService.encrypt(xx.getId()));
                item.set("name", xx.getName());
                item.set("money", xx.getMoney());
                item.set("installServiceCode", xx.getServiceCode());
                item.set("peopleAmount", xx.getPeopleAmount());
                item.set("type", xx.getType());
                item.set("remark", xx.getRemark());
                item.set("createdBy", xx.getCreatedBy());
                item.set("creationDate", DateUtil.formatDateTime(xx.getCreationDate()));

                String serviceZone = xx.getServiceZone();
                if (StrUtil.isNotEmpty(serviceZone)) {

                    List<ServiceZoneDTO> listServiceZone = JSONUtil.toList(serviceZone, ServiceZoneDTO.class);

                    List<Dict> listDict = listServiceZone.stream().map(ooo -> {
                        Dict itemDict = Dict.create().parseBean(ooo);

                        ResponseEntity<ChinaZoingRemoteVO> showDetails = chinaZoingRemoteService.showDetails(ooo.getCityId());
                        if (HttpStatus.OK.equals(showDetails.getStatusCode())) {
                            ChinaZoingRemoteVO detailsBody = showDetails.getBody();
                            if (null != detailsBody) {
                                itemDict.set("cityDetail", Dict.create()
                                        .set("levelThreeAreaName", detailsBody.getLevelThreeAreaName())
                                        .set("levelTwoAreaName", detailsBody.getLevelTwoAreaName())
                                        .set("levelOneAreaName", detailsBody.getLevelOneAreaName())
                                );
                            } else {
                                itemDict.set("cityDetail", null);
                            }
                        }

                        return itemDict;
                    }).collect(Collectors.toList());

                    item.set("serviceZone", listDict);
                }

                return item;
            }).collect(Collectors.toList());
            return new Page<>(dictList, new PageInfo(page.getTotalPages(), page.getSize()), page.getTotalElements());
        }

        return new Page<>(new ArrayList<>(), new PageInfo(0, page.getSize()), 0);
    }

    @Override
    public Dict detail(Long id) {
        InstallService installService = installServiceRepository.selectByPrimaryKey(id);

        if (ObjectUtil.isNotNull(installService)) {
            Dict dict = Dict.create();

            dict.set("id", myEncryptionService.encrypt(installService.getId()));
            dict.set("name", installService.getName());
            dict.set("money", installService.getMoney());
            dict.set("installServiceCode", installService.getServiceCode());
            dict.set("peopleAmount", installService.getPeopleAmount());
            dict.set("type", installService.getType());
            dict.set("remark", installService.getRemark());
            dict.set("createdBy", installService.getCreatedBy());
            dict.set("objectVersionNumber", installService.getObjectVersionNumber());
            dict.set("creationDate", DateUtil.formatDateTime(installService.getCreationDate()));

            String serviceZone = installService.getServiceZone();
            if (StrUtil.isNotEmpty(serviceZone)) {
                List<ServiceZoneDTO> listServiceZone = JSONUtil.toList(serviceZone, ServiceZoneDTO.class);

                List<Dict> listDict = listServiceZone.stream().map(ooo -> {
                    Dict itemDict = Dict.create().parseBean(ooo);

                    ResponseEntity<ChinaZoingRemoteVO> showDetails = chinaZoingRemoteService.showDetails(ooo.getCityId());
                    if (HttpStatus.OK.equals(showDetails.getStatusCode())) {
                        ChinaZoingRemoteVO detailsBody = showDetails.getBody();
                        if (null != detailsBody) {
                            itemDict.set("cityDetail", Dict.create()
                                    .set("levelThreeAreaName", detailsBody.getLevelThreeAreaName())
                                    .set("levelTwoAreaName", detailsBody.getLevelTwoAreaName())
                                    .set("levelOneAreaName", detailsBody.getLevelOneAreaName())
                            );
                        } else {
                            itemDict.set("cityDetail", null);
                        }
                    }

                    return itemDict;
                }).collect(Collectors.toList());

                dict.set("serviceZone", listDict);
            }
            return dict;
        }
        return Dict.create().parseBean(installService);
    }

    @Override
    public Boolean create(InstallServiceDTO dto) {
        if (CollUtil.isEmpty(dto.getServiceZone())) {
            throw new CommonException("没有设置服务区域!");
        }
        InstallService installService = new InstallService();
        BeanUtils.copyProperties(dto, installService);

        if (StrUtil.isEmpty(dto.getServiceCode())) {
            installService.setServiceCode(RandomUtil.randomString(6) + RandomUtil.randomNumbers(3) + RandomUtil.randomString(2));
        }

        // 获取租户的id
        CustomUserDetails userDetails = DetailsHelper.getUserDetails();
        Long tenantId = userDetails.getTenantId();
        installService.setServiceZone(JSONUtil.parse(dto.getServiceZone()).toString());
        installService.setDeleteFlag(false);
        installService.setTenantId(tenantId);

        return installServiceRepository.insert(installService) > 0;
    }

    @Override
    public Boolean update(InstallServiceDTO dto) {
        InstallService installService = new InstallService();
        BeanUtils.copyProperties(dto, installService);

        if (CollUtil.isNotEmpty(dto.getServiceZone())) {
            installService.setServiceZone(JSONUtil.parse(dto.getServiceZone()).toString());
        }

        return installServiceRepository.updateByPrimaryKeySelective(installService) > 0;
    }

    @Override
    public Boolean batchRemove(List<Long> ids) {
        List<InstallService> installServiceList = installServiceRepository.selectByCondition(Condition.builder(InstallService.class)
                .andWhere(Sqls.custom()
                        .andIn(InstallService.FIELD_ID, ids, true))
                .build());

        return installServiceRepository.batchDelete(installServiceList) > 0;
    }
}
