package org.skyer.afterSales.app.service;

import cn.hutool.core.lang.Dict;
import org.skyer.afterSales.domain.dto.LogisticsCompanyDTO;
import org.skyer.afterSales.domain.dto.PageLogisticsCompanyDTO;
import org.skyer.afterSales.domain.entity.LogisticsCompany;
import org.skyer.core.domain.Page;
import org.skyer.mybatis.pagehelper.domain.PageRequest;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * 物流商表应用服务
 *
 * @author chenzz 2021-09-13 10:39:10
 */
public interface LogisticsCompanyService {
    /**
     * 列表
     *
     * @param queryDto
     * @return
     */
    List<LogisticsCompany> list(PageLogisticsCompanyDTO queryDto);

    /**
     * 分页
     *
     * @param pageRequest
     * @param queryDto
     * @return
     */
    Page<Dict> page(PageRequest pageRequest, PageLogisticsCompanyDTO queryDto);

    /**
     * 详情
     *
     * @param id
     * @return
     */
    Dict detail(Long id);

    /**
     * 添加，创建
     *
     * @param addDto
     * @return
     */
    Boolean create(@RequestBody LogisticsCompanyDTO addDto);

    /**
     * 更新，修改
     *
     * @param editDto
     * @return
     */
    Boolean update(@RequestBody LogisticsCompanyDTO editDto);

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    int batchRemove(List<Long> ids);
}
