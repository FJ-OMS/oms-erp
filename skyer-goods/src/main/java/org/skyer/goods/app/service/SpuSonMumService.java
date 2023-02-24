package org.skyer.goods.app.service;

import cn.hutool.core.lang.Dict;
import org.skyer.core.domain.Page;
import org.skyer.goods.domain.dto.PageSonMumDTO;
import org.skyer.goods.domain.dto.SpuSonMumDTO;
import org.skyer.mybatis.pagehelper.domain.PageRequest;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * 子母商品应用服务
 *
 * @author chenzz 2021-08-16 15:04:38
 */
public interface SpuSonMumService {

    /**
     * 分页
     *
     * @param pageRequest
     * @param spuSonMum
     * @return
     */
    Page<Dict> page(PageRequest pageRequest, PageSonMumDTO spuSonMum);

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
     * @param spuSonMumDTO
     * @return
     */
    Boolean create(@RequestBody SpuSonMumDTO spuSonMumDTO);

    /**
     * 更新，修改
     *
     * @param spuSonMumDTO
     * @return
     */
    Boolean update(@RequestBody SpuSonMumDTO spuSonMumDTO);

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    Boolean batchRemove(List<Long> ids);
}
