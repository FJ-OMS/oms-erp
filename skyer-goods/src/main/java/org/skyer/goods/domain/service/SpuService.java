package org.skyer.goods.domain.service;

import org.skyer.core.domain.Page;
import org.skyer.goods.api.dto.GoodsPageReqDTO;
import org.skyer.goods.api.dto.GoodsReqDTO;
import org.skyer.goods.api.dto.GoodsRequestDTO;
import org.skyer.goods.domain.entity.Spu;
import org.skyer.goods.domain.vo.GoodsDetailResVO;
import org.skyer.goods.domain.vo.GoodsDetailsVO;
import org.skyer.goods.domain.vo.GoodsPageResVO;
import org.skyer.mybatis.base.BaseRepository;

import java.util.List;

/**
 * 商品表服务接口
 *
 * @author chenzz
 * @date 2021-06-25 13:36:41
 */
public interface SpuService extends BaseRepository<Spu> {
    /**
     * 商品列表分页
     * @param goodsPageReqDTO
     * @return
     */
    Page<GoodsPageResVO> pageGoods(GoodsPageReqDTO goodsPageReqDTO);

    /**
     * 查询spu的商品List
     * @param spuList
     * @return
     */
    List<GoodsPageResVO> queryGoodsBySpuList(List<String> spuList);

    /**
     * 保存或者更新商品
     * @param goodsReqDTO
     */
    void saveOrUpdateGoods(GoodsReqDTO goodsReqDTO);

    /**
     * 删除商品
     * @param ids
     */
    void deleteByIds(List<Long> ids);

    /**
     * 查询商品详情
     * @param id
     * @return
     */
    GoodsDetailResVO queryDetail(Long id);

    /**
     * 修改状态
     * @param ids
     * @param status
     */
    void updateGoodsStatus(List<Long> ids,Integer status);


    /**
     * 保存或者更新商品
     * @param goodsRequestDTO
     */
    void insertOrUpdateGoods(GoodsRequestDTO goodsRequestDTO);


    /**
     * 通过商品ID查详情
     * @param id
     * @return
     */
    GoodsDetailsVO getDetailsById(Long id);
}