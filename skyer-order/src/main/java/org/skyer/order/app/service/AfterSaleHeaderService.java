package org.skyer.order.app.service;

import java.util.List;

import org.skyer.order.api.dto.*;
import org.skyer.order.api.vo.WarehouseWarrantResultVO;
import org.skyer.order.domain.entity.AfterSaleHeader;
import org.skyer.order.domain.entity.ThirdPartyAfterSaleHeader;
import org.skyer.order.api.dto.*;

/**
 * 售后单应用服务
 *
 * @author lzh 2022-03-16 10:55:39
 */
public interface AfterSaleHeaderService {

	/**
	 *  生成入库单前置校验
	 * @param afterSaleHeader
	 */
	void doCreateReturnGoodsWarehousingPrepare(AfterSaleHeader afterSaleHeader);

	/**
	 * 生成入库通知单
	 * @param afterSaleHeader
	 * @return
	 */
	WarehouseWarrantSaveDTO createReturnGoodsWarehousingNotice(AfterSaleHeader afterSaleHeader);

	/**
	 * 入库通知单回写
	 * @param warehouseWarrantResultVO
	 */
	void writeBackResult(WarehouseWarrantResultVO warehouseWarrantResultVO);

	/**
	 * 退货二审过后上传物流信息
	 * @param headerId
	 * @param key
	 */
	void uploadLogistics(Long headerId, long key);

	/**
	 * 入库处理
	 * @param headerId
	 * @param key
	 */
	void wareDeal(Long headerId, long key);
}
