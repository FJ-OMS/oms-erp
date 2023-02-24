package org.skyer.goods.domain.service;

import org.skyer.core.domain.Page;
import org.skyer.goods.api.dto.GoodsGroupReqDTO;
import org.skyer.goods.api.dto.GroupPageReqDTO;
import org.skyer.goods.api.dto.IdsReqDTO;
import org.skyer.goods.domain.vo.GroupPageResVO;
import org.skyer.goods.domain.vo.SkuGroupResVO;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Sku组合表应用服务
 *
 * @author wushaochuan 2021-08-13 09:55:04
 */
public interface SkuGroupService {

    /**
     * 创建或者更新商品组
     * @param goodsGroupReqDTO
     */
    void createOrUpdateSkuGroup(@RequestBody GoodsGroupReqDTO goodsGroupReqDTO);

    /**
     * sku组合详情
     * @param groupId
     * @return
     */
    SkuGroupResVO skuGroupDetail(Long groupId);

    /**
     * 商品组分页
     * @param groupPageReqDTO
     * @return
     */
    Page<GroupPageResVO> pageGroup(GroupPageReqDTO groupPageReqDTO);

    /**
     * 删除商品组合
     * @param idsReqDTO
     */
    void delGroup(IdsReqDTO idsReqDTO);
}
