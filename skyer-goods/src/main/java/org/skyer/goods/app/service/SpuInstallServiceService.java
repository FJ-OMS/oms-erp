package org.skyer.goods.app.service;

import cn.hutool.core.lang.Dict;
import org.skyer.core.domain.Page;
import org.skyer.goods.domain.dto.PageSpuInstallServiceDTO;
import org.skyer.goods.domain.dto.SpuInstallServiceDTO;
import org.skyer.mybatis.pagehelper.domain.PageRequest;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * 商品安装服务应用服务
 *
 * @author chenzz 2021-08-18 16:42:11
 */
public interface SpuInstallServiceService {
    /**
     * 分页
     *
     * @param pageRequest
     * @param spuInstallService
     * @return
     */
    Page<Dict> page(PageRequest pageRequest, PageSpuInstallServiceDTO spuInstallService);

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
     * @param spuInstallServiceDTO
     * @return
     */
    Boolean create(@RequestBody SpuInstallServiceDTO spuInstallServiceDTO);

    /**
     * 更新，修改
     *
     * @param spuInstallServiceDTO
     * @return
     */
    Boolean update(@RequestBody SpuInstallServiceDTO spuInstallServiceDTO);

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    Boolean batchRemove(List<Long> ids);
}
