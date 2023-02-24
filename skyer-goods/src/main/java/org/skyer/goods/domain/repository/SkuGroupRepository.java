package org.skyer.goods.domain.repository;

import org.skyer.core.domain.Page;
import org.skyer.goods.api.dto.GroupPageReqDTO;
import org.skyer.goods.api.dto.IdsReqDTO;
import org.skyer.goods.domain.vo.GroupPageResVO;
import org.skyer.mybatis.base.BaseRepository;
import org.skyer.goods.domain.entity.SkuGroup;

import java.util.List;

/**
 * Sku组合表资源库
 *
 * @author wushaochuan 2021-08-13 09:55:04
 */
public interface SkuGroupRepository extends BaseRepository<SkuGroup> {

    /**
     * 通过名字获取商品套餐
     * @param name
     * @param tenantId
     * @return
     */
    List<SkuGroup> getSkuGroupByName(String name, Long tenantId);

    /**
     * 分页查询商品组合
     * @param groupPageReqDTO
     * @param tenantId
     * @return
     */
    Page<GroupPageResVO> pageGroup(GroupPageReqDTO groupPageReqDTO,Long tenantId);

    /**
     * 删除商品组合
     * @param idsReqDTO
     */
    void delGroup(IdsReqDTO idsReqDTO);
}
