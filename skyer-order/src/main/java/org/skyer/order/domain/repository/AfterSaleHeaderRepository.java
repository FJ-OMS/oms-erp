package org.skyer.order.domain.repository;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.skyer.core.domain.Page;
import org.skyer.mybatis.base.BaseRepository;
import org.skyer.order.api.dto.*;
import org.skyer.order.api.vo.*;
import org.skyer.order.app.dto.SaleOrderOrderApiDTO;
import org.skyer.order.app.vo.ChannelSaleOrderVO;
import org.skyer.order.domain.entity.AfterSaleHeader;
import org.skyer.order.domain.entity.ThirdPartyAfterSaleHeader;

/**
 * 售后单资源库
 *
 * @author lzh 2022-03-16 10:55:39
 */
public interface AfterSaleHeaderRepository extends BaseRepository<AfterSaleHeader> {

	/**
	 * 增加额外的仅退款商品
	 * @param addRefundOnlyDto
	 */
	void addRefundOnlyItemLine(AddRefundOnlyDTO addRefundOnlyDto);

	/**
	 * 增加额外的退货商品
	 * @param addRefundOnlyDto
	 */
	void addRefundGoodsItemLine(AddRefundOnlyDTO addRefundOnlyDto);


	/**
	 * 仅退款/退货退款删除商品行
	 * @param afterItemDeleteDto afterItemDeleteDto
	 */
	void refundOnlyDeleteItem(AfterItemDeleteDTO afterItemDeleteDto);

	/**
	 * 仅退款分页查询
	 * @param refundOnlyPageDto refundOnlyPageDto
	 * @return Page<RefundOnlyPageVO>
	 */
	Page<RefundOnlyPageVO> refundOnlyPage (RefundOnlyPageDTO refundOnlyPageDto);
	/**
	 * 创建仅退款单
	 * @param refundGoodsDTO refundGoodsDTO
	 */
	void createOnlyRefund(RefundGoodsDTO refundGoodsDTO);

	/**
	 * 创建退货退款单
	 * @param refundGoodsDTO
	 */
	void createRefundGoods(RefundGoodsDTO refundGoodsDTO);

	/**
	 * 退货退款单详情
	 * @param orderId
	 * @param refundCode
	 * @return
	 */
	RefundDetailsVO queryBaseDetail(Long orderId,String refundCode);

	/**
	 * 修改基本信息
	 * @param updateRefundDTO
	 */
	void editBase(UpdateRefundDTO updateRefundDTO);

	/**
	 * 退货退款管理
	 * @param refundPageDTO
	 * @return
	 */
	Page<RefundPageVO> refundPage(RefundPageDTO refundPageDTO);

	/**
	 * 查询退货物流信息
	 * @param id
	 * @return
	 */
	List<RefundsLogisticsVO> refundsLogistics(Long id);

	/**
	 * 审核页面
	 * @param refundPageDTO
	 * @return
	 */
	Page<RefundPageVO> refundAuditPage(RefundAuditPageDTO refundPageDTO);

	/**
	 * 批量审核
	 * @param listDTO
	 */
	void batchAuditRefund(List<AuditRefundDTO> listDTO);

	/**
	 * 终审信息插入
	 * @param saleHeader
	 */
	void getLogisticsInfo(AfterSaleHeader saleHeader);

	/**
	 * 同步正向商品
	 * @param itemIds
	 * @param refundFlag
	 * @param tagsFlag
	 */
	void syncItemsDeliveryStatus(List<Long> itemIds,Integer refundFlag,boolean tagsFlag,Integer returnFlag);

	/**
	 * 同步正向商品行
	 * @param itemLineIds
	 * @param refundFlag
	 * @param returnFlag
	 * @param tagsFlag
	 */
	void syncItemsLineDeliveryStatus(List<Long> itemLineIds, Integer refundFlag,Integer returnFlag, boolean tagsFlag);

	/**
	 * 根据退款单编号获取详情
     * @author lixiaoyang
	 * @date 2022/3/25 9:41
	 * @param refundCode
	 */
	List<AfterSaleDetailVO> getDetail(String refundCode);


	/**
	 * 	同步正向订单标签
	 * @param innerOrderNo innerOrderNo
	 * @param tagsFlag true为打标 false为去掉标签
	 * @param returnFlag 是否退货 true为是
	 */
	void headerMarkTags(String innerOrderNo,boolean tagsFlag,boolean returnFlag);

	/**
	 * 通过内部单号查询
	 * @param innerRefundNo
	 * @return
	 */
	AfterSaleHeader queryByRefundNo(String innerRefundNo);

	/**
	 * 对应退货单确认入库
	 * @param id
	 */
	void confirmWarehousing(Long id);


	/**
	 * 创建内部退货单头
	 * @param baseInfo
	 * @return String
	 */
	String createInnerReturnHeader(ThirdPartyAfterSaleHeader baseInfo);

	/**
	 * 第三方退货单数据校验
	 * @param outReturnNo 三方退货单号
	 * @param outOrderNo 三方订单号
	 */
	void compareThirdReturnOrderData(String outReturnNo,String outOrderNo);

	/**
	 * 通过外部售后单号查询
	 * @param outerRefundNo
	 * @return
	 */
	AfterSaleHeader queryByOutRefundNo(String outerRefundNo);

	/**
	 * 创建内部退货单商品明细
	 * @param outRefundNo
	 * @param outOrderNo
	 * @param innerRefundNo
	 * @param innerOrderNo
	 */
	void createReturnOrderDetails(String outRefundNo, String outOrderNo, String innerRefundNo,String innerOrderNo);

	/**
	 * 创建内部退货单物流明细
	 * @param outRefundNo
	 * @param outOrderNo
	 * @param innerRefundNo
	 * @param innerOrderNo
	 */
	void createReturnLogistics(String outRefundNo, String outOrderNo, String innerRefundNo,String innerOrderNo);

	/**
	 * 添加收货单收货信息
	 * @param outRefundNo
	 * @param outOrderNo
	 * @param innerRefundNo
	 * @param innerOrderNo
	 */
	void createReturnReceiver(String outRefundNo, String outOrderNo, String innerRefundNo,String innerOrderNo);


	/**
	 * 查询售后单报表
	 * @param orderSaleDTO
	 * @return
	 */
	List<OrderSaleReportVO> queryAfterHeaderToSaleReport(OrderSaleDTO orderSaleDTO);



	List<ChannelSaleOrderVO> queryPartAfterChannelSaleReport(SaleOrderOrderApiDTO saleOrderOrderApiDTO);


	List<OrderSaleDateVO> queryOrderSaleDateReport( OrderSaleDTO orderSaleDTO);


	/**
	 * 修改收货仓库
	 * @param updateWarehouseDTO
	 */
	void updateWarehouse(UpdateWarehouseDTO updateWarehouseDTO);
}
