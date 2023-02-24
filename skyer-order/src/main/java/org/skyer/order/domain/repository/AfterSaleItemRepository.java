package org.skyer.order.domain.repository;

import java.util.List;

import org.skyer.core.domain.Page;
import org.skyer.mybatis.base.BaseRepository;
import org.skyer.order.api.dto.AfterItemDeleteDTO;
import org.skyer.order.api.dto.AfterSaleItemDTO;
import org.skyer.order.api.vo.AfterSaleItemVO;
import org.skyer.order.domain.entity.AfterSaleItem;
import org.skyer.starter.keyencrypt.core.Encrypt;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 售后单商品资源库
 *
 * @author lzh 2022-03-16 10:55:40
 */
public interface AfterSaleItemRepository extends BaseRepository<AfterSaleItem> {

	/**
	 * 查询售后商品
	 * @param innerNo
	 * @return
	 */
	List<AfterSaleItem> queryByInnerNo(String innerNo);

	/**
	 * 分页查询可退货/退款商品
	 * @param afterSaleItemDto afterSaleItemDto
	 * @return Page<AfterSaleItemVO>
	 */
	Page<AfterSaleItemVO> returnPageByInnerNo(AfterSaleItemDTO afterSaleItemDto);

	/**
	 *  根据itemIds获取可退货的itemLine
	 * @param originItemIds originItemIds
	 * @return List<AfterSaleItem>
	 */
	List<AfterSaleItem> returnGoodsListByItemIds(List<Long> originItemIds);

	/**
	 * 查询商品
	 * @param innerRefundNo
	 * @return
	 */
	List<AfterSaleItem> queryByInnerRefundNo(String innerRefundNo);
}
