package org.skyer.afterSales.app.service;

import cn.hutool.core.lang.Dict;
import org.skyer.afterSales.domain.dto.InstallOrderDTO;
import org.skyer.afterSales.domain.dto.PageInstallOrderDTO;
import org.skyer.afterSales.domain.entity.InstallOrder;
import org.skyer.core.domain.Page;
import org.skyer.mybatis.pagehelper.domain.PageRequest;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * 安装工单表应用服务
 *
 * @author chenzz 2021-09-13 14:46:41
 */
public interface InstallOrderService {
    /**
     * 列表
     *
     * @param queryDto
     * @return
     */
    List<InstallOrder> list(PageInstallOrderDTO queryDto);

    /**
     * 分页
     *
     * @param pageRequest
     * @param queryDto
     * @return
     */
    Page<Dict> page(PageRequest pageRequest, PageInstallOrderDTO queryDto);

    /**
     * 详情
     *
     * @param code
     * @return
     */
    Dict detail(String code);

    /**
     * 添加，创建
     *
     * @param addDto
     * @return
     */
    Boolean create(@RequestBody InstallOrderDTO addDto);

    /**
     * 更新，修改
     *
     * @param editDto
     * @return
     */
    Boolean update(@RequestBody InstallOrderDTO editDto);

    /**
     * 批量删除
     *
     * @param codes
     * @return
     */
    int batchRemove(List<String> codes);
}
