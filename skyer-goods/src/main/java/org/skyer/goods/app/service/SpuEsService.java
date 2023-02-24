package org.skyer.goods.app.service;

import org.skyer.core.domain.Page;
import org.skyer.goods.api.dto.GoodsPageReqDTO;
import org.skyer.goods.api.dto.SpuListDTO;
import org.skyer.goods.domain.entity.Spu;
import org.skyer.goods.domain.vo.GoodsPageResVO;

import java.io.IOException;
import java.util.List;
import java.util.function.Supplier;

/**
 * 查询或者更新es
 *
 * @description
 * @Author lzh
 * @create 2022/1/6
 */
public interface SpuEsService {

	/**
	 * 生成spu集合数据，同步到es
	 * @param spuListDTO 包含spu列表的DTO
	 */
	SpuListDTO syncSpuEsSage(SpuListDTO spuListDTO);

	/**
	 * saga task消费同步es数据
	 * @param spuListJSONStr spuListDTO的json串
	 * @return SpuListDTO
	 * @throws IOException
	 */
	SpuListDTO syncSpuEsSageTask(String spuListJSONStr) throws IOException;

	/**
	 * 将spu数据存入es
	 *
	 * @param
	 */
	void createOrUpdateSpuEs(Spu spu);

	/**
	 * 查询商品
	 * @param goodsPageReqDTO
	 * @return
	 */
	Page<GoodsPageResVO> querySpuEs(GoodsPageReqDTO goodsPageReqDTO) throws Exception;

	/**
	 * 删除es数据
	 */
	void delSpuEs(List<Spu> spuList);

	/**
	 * 清空库存所有记录
	 */
	void delSpuEsAll();

	/**
	 * 批量导入商品数据
	 * @return
	 * @throws IOException
	 */
	void productStatusUp() throws IOException;
}
