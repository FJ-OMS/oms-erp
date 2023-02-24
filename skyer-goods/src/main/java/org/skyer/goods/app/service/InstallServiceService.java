package org.skyer.goods.app.service;

import cn.hutool.core.lang.Dict;
import org.skyer.core.domain.Page;
import org.skyer.goods.domain.dto.InstallServiceDTO;
import org.skyer.goods.domain.dto.PageInstallServiceDTO;
import org.skyer.goods.domain.entity.InstallService;
import org.skyer.mybatis.pagehelper.domain.PageRequest;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * 安装服务应用服务
 *
 * @author chenzz 2021-08-18 15:42:47
 */
public interface InstallServiceService {

    /**
     * 列表
     *
     * @param installServiceDTO
     * @return
     */
    List<InstallService> list(PageInstallServiceDTO installServiceDTO);

    /**
     * 分页
     *
     * @param pageRequest
     * @param installServiceDTO
     * @return
     */
    Page<Dict> page(PageRequest pageRequest, PageInstallServiceDTO installServiceDTO);

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
     * @param dto
     * @return
     */
    Boolean create(@RequestBody InstallServiceDTO dto);

    /**
     * 更新，修改
     *
     * @param dto
     * @return
     */
    Boolean update(@RequestBody InstallServiceDTO dto);

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    Boolean batchRemove(List<Long> ids);

}
