package org.skyer.goods.app.service;

import org.skyer.core.domain.Page;
import org.skyer.goods.api.dto.GoodsSkuPageDTO;
import org.skyer.goods.domain.entity.Sku;
import org.skyer.goods.domain.vo.GoodsSkuPageVO;

import java.io.IOException;
import java.util.List;
import java.util.function.Supplier;

/**
 * @author zhanglb
 */
public interface SkuEsService {


    /**
     * 创建skuList异步saga任务
     * @param skuList  skuList
     * @return List
     */
    List<Sku> syncSkuListEsSage(List<Sku> skuList);

    /**
     * 消费异步saga任务
     * @param skuListJsonStr skuListJsonStr
     * @return List<Sku>
     * @throws IOException 转换异常
     */
    List<Sku> syncSkuListEsSageTask(String skuListJsonStr) throws IOException;

    /**
     * 批量删除es数据 到saga
     * @param skuList skuList
     * @return  List<Sku>
     */
    List<Sku> syncDelSkuListEsSage(List<Sku> skuList);

    /**
     *  批量删除es数据
     * @param skuListJsonStr skuListJsonStr
     * @return List<Sku>
     * @throws IOException 删除异常
     */
    List<Sku> syncDelSkuListEsSageTask(String skuListJsonStr) throws IOException;

    /**
     * 清空sku所有记录
     */
    void delSkuEsAll() throws Exception;

    /**
     * es查询sku列表
     * @param goodsSkuPageDTO skuPage
     * @return Page<GoodsSkuPageVO>
     */
     Page<GoodsSkuPageVO> listPage(GoodsSkuPageDTO goodsSkuPageDTO) throws Exception;

    /**
     * 批量导入sku的所有数据
     * @throws IOException 导入异常
     */
    void skuStatusUp() throws IOException;
}
