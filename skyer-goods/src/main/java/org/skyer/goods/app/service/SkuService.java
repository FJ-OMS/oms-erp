package org.skyer.goods.app.service;

import org.skyer.core.domain.Page;
import org.skyer.goods.api.dto.*;
import org.skyer.goods.domain.dto.AuditDTO;
import org.skyer.goods.domain.dto.SkuAuditDTO;
import org.skyer.goods.domain.vo.*;
import org.skyer.mybatis.pagehelper.domain.PageRequest;

import java.util.List;

/**
 * 商品规格表应用服务
 *
 * @author chenzz 2021-07-06 10:35:43
 */
public interface SkuService {

    /**
     * sku商品分页，返回商品的资料
     * @param goodsSku
     * @return
     */
    Page<GoodsSkuPageVO> listPage( GoodsSkuPageDTO goodsSku);

    /**
     * 查看商品详情
     * @param id skuId
     * @param isEdit 是否编辑
     * @return
     */
    GoodsSkuDetailVO getDetail(Long id, boolean isEdit);

    /**
     * 保存sku
     *
     * @param skuBatchSaveDTO
     * @return
     */
    Boolean saveSku(SkuBatchSaveDTO skuBatchSaveDTO);

    /**
     * 删除sku
     *
     * @param ids
     * @return
     */
    Boolean deleteByIds(List<Long> ids);

    /**
     * 变更sku信息
     *
     * @param updateDTO
     * @return
     */
    boolean updateSku(SkuUpdateDTO updateDTO);

    /**
     * 批量提交数据
     *
     * @param batchAuditDTO
     * @return
     */
    int batchAudit(SkuBatchAuditDTO batchAuditDTO);


    /**
     * 根据商品id获取sku列表和属性列表
     *
     * @param spuId
     * @return
     */
    List<GoodsSkuDetailVO> getSkuAndAttributeDataByGoodsId(Long spuId);


    /**
     * 查询rpc商品
     *
     * @param skuCodeList
     * @return
     */
    List<RpcGoodsResVO> queryRPCGoodsList(List<String> skuCodeList);


    /**
     * 查询套餐内的详情
     * @param skuCodeList
     * @return
     */
    List<RpcOrderResVO> getMealsGoodsList(List<String> skuCodeList);
    /**
     * 查询订单RpcVo
     * @param skuCodeList skuList
     * @return List<RpcOrderResVO>
     */
    List<RpcOrderResVO> queryOrderRpcList(List<String> skuCodeList);


    /**
     * 获取审核详情
     *
     * @author lixiaoyang
     * @date 2021/12/30 11:11
     */
    SkuAuditVO getAuditDetail(SkuAuditDTO dto);

    /**
     * SKU审核操作
     *
     * @author lixiaoyang
     * @date 2021/12/30 14:44
     */
    Boolean skuAudit(AuditDTO dto);
}
