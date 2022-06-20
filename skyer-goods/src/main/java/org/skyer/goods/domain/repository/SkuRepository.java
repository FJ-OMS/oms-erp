package org.skyer.goods.domain.repository;

import org.skyer.core.domain.Page;
import org.skyer.goods.api.dto.GoodsSkuPageDTO;
import org.skyer.goods.domain.vo.RpcGoodsResVO;
import org.skyer.goods.domain.vo.RpcOrderResVO;
import org.skyer.mybatis.base.BaseRepository;
import org.skyer.goods.domain.entity.Sku;
import org.skyer.mybatis.pagehelper.domain.PageRequest;

import java.util.List;

/**
 * 商品规格表资源库
 *
 * @author zhanglongbing@ebc.com 2021-12-27 14:48:20
 */
public interface SkuRepository extends BaseRepository<Sku> {

    /**
     * 分页查询
     * @param goodsSku
     * @return
     */
    Page<Sku> pageSku(GoodsSkuPageDTO goodsSku);

    /**
     * 校验该属性值是否生成过sku
     * @param spuId
     * @param md5Long
     * @return
     */
    Sku checkHaveSameSku(Long spuId,String md5Long);

    /**
     * 校验skuCode是否生成过sku
     * @param skuCode
     * @return
     */
    Sku checkHaveSameSkuCode(String skuCode);

    /**
     * 校验skuCode是否生成过sku
     * @param skuCode
     * @return
     */
    Sku checkHaveSameSkuCodeWithOutSkuId(String skuCode,Long skuId);

    /**
     * 通过spuId查询sku
     * @param tenantId
     * @param spuId
     * @return
     */
    List<Sku> getSkuListBySpuId(Long tenantId, Long spuId);

    /**
     * 根据skuCodeList获取sku列表
     * @param skuCodeList
     * @return
     */
    List<Sku> getSkuListBySkuCode(List<String> skuCodeList);

    /**
     * skulist
     * @param skuIdList
     * @return
     */
    List<Sku> getSkuListBySkuId(List<Long> skuIdList);

    /**
     *
     * @param attributeValue
     * @param spuId
     * @param skuId
     * @return
     */
    Boolean getAttributeValueAndSpu(String attributeValue,Long spuId,Long skuId);

    /**
     * 根据code获取sku
     * @param code
     * @return
     */
    Sku getSkuByCode(String code);

    /**
     * 查询skuCode(rpc调用 )
     * @param skuCodeList
     * @return
     */
    List<RpcGoodsResVO> queryRPCGoodsList(List<String> skuCodeList);

    /**
     * 查询skuCode(rpc调用 )
     * @param skuCodeList
     * @return
     */
    List<RpcOrderResVO> queryOrderRpcList(List<String> skuCodeList);
}
