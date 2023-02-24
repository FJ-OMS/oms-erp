package org.skyer.afterSales.app.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Dict;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import org.skyer.afterSales.app.service.LogisticsCompanyService;
import org.skyer.afterSales.domain.dto.LogisticsCompanyDTO;
import org.skyer.afterSales.domain.dto.PageLogisticsCompanyDTO;
import org.skyer.afterSales.domain.entity.LogisticsCompany;
import org.skyer.afterSales.domain.repository.LogisticsCompanyRepository;
import org.skyer.core.domain.Page;
import org.skyer.core.domain.PageInfo;
import org.skyer.core.oauth.CustomUserDetails;
import org.skyer.core.oauth.DetailsHelper;
import org.skyer.mybatis.domian.Condition;
import org.skyer.mybatis.pagehelper.PageHelper;
import org.skyer.mybatis.pagehelper.domain.PageRequest;
import org.skyer.mybatis.util.Sqls;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 物流商表应用服务默认实现
 *
 * @author chenzz 2021-09-13 10:39:10
 */
@Service
public class LogisticsCompanyServiceImpl implements LogisticsCompanyService {
    @Autowired
    private LogisticsCompanyRepository logisticsCompanyRepository;

    @Override
    public List<LogisticsCompany> list(PageLogisticsCompanyDTO queryDto) {
        // 根据条件查找
        return logisticsCompanyRepository.selectByCondition(Condition.builder(LogisticsCompany.class)
                .andWhere(Sqls.custom()
                        .andLike(LogisticsCompany.FIELD_CODE, queryDto.getCode(), true)
                        .andEqualTo(LogisticsCompany.FIELD_ENABLE_FLAG, queryDto.getEnableFlag(), true)
                        .andEqualTo(LogisticsCompany.FIELD_DELETE_FLAG, true))
                .build());
    }

    @Override
    public Page<Dict> page(PageRequest pageRequest, PageLogisticsCompanyDTO queryDto) {
        Page<LogisticsCompany> page = PageHelper.doPageAndSort(pageRequest, () -> logisticsCompanyRepository.selectByCondition(Condition.builder(LogisticsCompany.class)
                .andWhere(Sqls.custom()
                        .andLike(LogisticsCompany.FIELD_CODE, queryDto.getCode(), true)
                        .andLike(LogisticsCompany.FIELD_NAME, queryDto.getName(), true)
                        .andEqualTo(LogisticsCompany.FIELD_ENABLE_FLAG, queryDto.getEnableFlag(), true)
                        .andEqualTo(LogisticsCompany.FIELD_DELETE_FLAG, false))
                .build()));

        List<LogisticsCompany> pageListContent = page.getContent();
        if (CollUtil.isNotEmpty(pageListContent)) {
            List<Dict> dictList = pageListContent.stream().map(xx -> {
                Dict item = Dict.create();

                item.set("id", xx.getId());
                item.set("name", xx.getName());
                item.set("code", xx.getCode());
                item.set("enableFlag", xx.getEnableFlag());
                item.set("tenantId", xx.getTenantId());

                item.set("createdBy", xx.getCreatedBy());
                item.set("objectVersionNumber", xx.getObjectVersionNumber());
                item.set("creationDate", DateUtil.formatDateTime(xx.getCreationDate()));

                return item;
            }).collect(Collectors.toList());
            return new Page<>(dictList, new PageInfo(page.getTotalPages(), page.getSize()), page.getTotalElements());
        }

        return new Page<>(new ArrayList<>(), new PageInfo(0, page.getSize()), 0);
    }

    @Override
    public Dict detail(Long id) {
        LogisticsCompany logisticsCompany = logisticsCompanyRepository.selectByPrimaryKey(id);
        if (ObjectUtil.isNotNull(logisticsCompany)) {
            Dict dict = Dict.create();

            dict.set("id", logisticsCompany.getId());
            dict.set("name", logisticsCompany.getName());
            dict.set("code", logisticsCompany.getCode());
            dict.set("enableFlag", logisticsCompany.getEnableFlag());
            dict.set("tenantId", logisticsCompany.getTenantId());

            dict.set("createdBy", logisticsCompany.getCreatedBy());
            dict.set("objectVersionNumber", logisticsCompany.getObjectVersionNumber());
            dict.set("creationDate", DateUtil.formatDateTime(logisticsCompany.getCreationDate()));

            return dict;
        }
        return null;
    }

    @Override
    public Boolean create(LogisticsCompanyDTO addDto) {
        LogisticsCompany logisticsCompany = new LogisticsCompany();
        BeanUtils.copyProperties(addDto, logisticsCompany);

        if (StrUtil.isEmpty(addDto.getCode())) {
            logisticsCompany.setCode(RandomUtil.randomString(6) + RandomUtil.randomNumbers(3) + RandomUtil.randomString(2));
        }

        // 获取租户的id
        CustomUserDetails userDetails = DetailsHelper.getUserDetails();
        Long tenantId = ObjectUtil.isNotNull(userDetails) ? userDetails.getTenantId() : 0;
        logisticsCompany.setDeleteFlag(false);
        logisticsCompany.setTenantId(tenantId);

        return logisticsCompanyRepository.insertSelective(logisticsCompany) > 0;
    }

    @Override
    public Boolean update(LogisticsCompanyDTO editDto) {
        LogisticsCompany logisticsCompany = new LogisticsCompany();
        BeanUtils.copyProperties(editDto, logisticsCompany);

        return logisticsCompanyRepository.updateByPrimaryKeySelective(logisticsCompany) > 0;
    }

    @Override
    public int batchRemove(List<Long> ids) {
        List<LogisticsCompany> list = logisticsCompanyRepository.selectByCondition(Condition.builder(LogisticsCompany.class)
                .andWhere(Sqls.custom()
                        .andIn(LogisticsCompany.FIELD_ID, ids, true))
                .build());

        return logisticsCompanyRepository.batchDelete(list);
    }
}
