package org.skyer.afterSales.app.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Dict;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import org.skyer.afterSales.app.service.InstallOrderService;
import org.skyer.afterSales.domain.dto.InstallOrderDTO;
import org.skyer.afterSales.domain.dto.PageInstallOrderDTO;
import org.skyer.afterSales.domain.entity.InstallOrder;
import org.skyer.afterSales.domain.repository.InstallOrderRepository;
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
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 安装工单表应用服务默认实现
 *
 * @author chenzz 2021-09-13 14:46:41
 */
@Service
public class InstallOrderServiceImpl implements InstallOrderService {
    @Autowired
    private InstallOrderRepository installOrderRepository;

    @Override
    public List<InstallOrder> list(PageInstallOrderDTO queryDto) {
        // 根据条件查找
        return installOrderRepository.selectByCondition(Condition.builder(InstallOrder.class)
                .andWhere(Sqls.custom()
                        .andLike(InstallOrder.FIELD_INNER_ORDER_NO, queryDto.getOrderNo(), true)
                        .andEqualTo(InstallOrder.FIELD_SHOP_NAME, queryDto.getShopName(), true)
                        .andEqualTo(InstallOrder.FIELD_DELETE_FLAG, false))
                .build());
    }

    @Override
    public Page<Dict> page(PageRequest pageRequest, PageInstallOrderDTO queryDto) {
        Page<InstallOrder> page = PageHelper.doPageAndSort(pageRequest, () -> installOrderRepository.selectByCondition(Condition.builder(InstallOrder.class)
                .andWhere(Sqls.custom()
                        .andLike(InstallOrder.FIELD_INNER_ORDER_NO, queryDto.getOrderNo(), true)
                        .andEqualTo(InstallOrder.FIELD_SHOP_NAME, queryDto.getShopName(), true)
                        .andEqualTo(InstallOrder.FIELD_DELETE_FLAG, false))
                .build()));

        List<InstallOrder> pageListContent = page.getContent();
        if (CollUtil.isNotEmpty(pageListContent)) {
            List<Dict> dictList = pageListContent.stream().map(xx -> {
                Dict item = Dict.create();

                item.set("id", xx.getId());

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
    public Dict detail(String code) {
        List<InstallOrder> list = installOrderRepository.selectByCondition(Condition.builder(InstallOrder.class)
                .andWhere(Sqls.custom()
                        .andEqualTo(InstallOrder.FIELD_INSTALL_ORDER_NO, code, true))
                .build());

        InstallOrder installOrder = CollUtil.isNotEmpty(list) ? list.get(0) : null;
        if (ObjectUtil.isNotNull(installOrder)) {
            Dict dict = Dict.create();

            dict.set("id", installOrder.getId());

            dict.set("tenantId", installOrder.getTenantId());

            dict.set("createdBy", installOrder.getCreatedBy());
            dict.set("objectVersionNumber", installOrder.getObjectVersionNumber());
            dict.set("creationDate", DateUtil.formatDateTime(installOrder.getCreationDate()));

            return dict;
        }
        return null;
    }

    @Override
    public Boolean create(InstallOrderDTO addDto) {
        InstallOrder installOrder = new InstallOrder();
        BeanUtils.copyProperties(addDto, installOrder);

        if (StrUtil.isEmpty(addDto.getInstallOrderCode())) {
            installOrder.setInstallOrderNo(RandomUtil.randomString(6) + RandomUtil.randomNumbers(3) + RandomUtil.randomString(2));
        }

        // 获取租户的id
        CustomUserDetails userDetails = DetailsHelper.getUserDetails();
        Long tenantId = ObjectUtil.isNotNull(userDetails) ? userDetails.getTenantId() : 0;
        installOrder.setDeleteFlag(false);
        installOrder.setTenantId(tenantId);

        return installOrderRepository.insertSelective(installOrder) > 0;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean update(InstallOrderDTO editDto) {
        List<InstallOrder> list = installOrderRepository.selectByCondition(Condition.builder(InstallOrder.class)
                .andWhere(Sqls.custom()
                        .andEqualTo(InstallOrder.FIELD_INSTALL_ORDER_NO, editDto.getInstallOrderCode(), true))
                .build());

        if (CollUtil.isNotEmpty(list)) {
            InstallOrder entityOne = list.get(0);

            InstallOrder updateEntity = new InstallOrder();
            BeanUtils.copyProperties(editDto, updateEntity);
            updateEntity.setId(entityOne.getId());

            return installOrderRepository.updateByPrimaryKeySelective(entityOne) > 0;
        }
        return false;
    }

    @Override
    public int batchRemove(List<String> codes) {
        List<InstallOrder> list = installOrderRepository.selectByCondition(Condition.builder(InstallOrder.class)
                .andWhere(Sqls.custom()
                        .andIn(InstallOrder.FIELD_INSTALL_ORDER_NO, codes, true))
                .build());

        return installOrderRepository.batchDelete(list);
    }
}
