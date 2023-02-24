package org.skyer.order.domain.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.validation.constraints.NotBlank;

import org.skyer.mybatis.domain.AuditDomain;
import org.skyer.mybatis.annotation.ModifyAudit;
import org.skyer.mybatis.annotation.VersionAudit;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 内部售后收货
 *
 * @author linzhuanghuang@ebc.com 2022-04-26 17:19:33
 */
@ApiModel("内部售后收货")
@ModifyAudit
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@Table(name = "sord_after_sale_receiver_info")
@Data
public class AfterSaleReceiverInfo extends AuditDomain {

	public static final String FIELD_ID = "id";
	public static final String FIELD_INNER_REFUND_NO = "innerRefundNo";
	public static final String FIELD_INNER_ORDER_NO = "innerOrderNo";
	public static final String FIELD_OUTER_ORDER_NO = "outerOrderNo";
	public static final String FIELD_OUTER_REFUND_NO = "outerRefundNo";
	public static final String FIELD_RETURN_NAME = "returnName";
	public static final String FIELD_MOBILE = "mobile";
	public static final String FIELD_PROVINCE_CODE = "provinceCode";
	public static final String FIELD_PROVINCE = "province";
	public static final String FIELD_CITY_CODE = "cityCode";
	public static final String FIELD_CITY = "city";
	public static final String FIELD_DISTRICT_CODE = "districtCode";
	public static final String FIELD_DISTRICT = "district";
	public static final String FIELD_TOWN = "town";
	public static final String FIELD_ADDRESS = "address";
	public static final String FIELD_RECEIVE_NAME = "receiveName";
	public static final String FIELD_RECEIVE_MOBILE = "receiveMobile";
	public static final String FIELD_RECEIVE_PROVINCE_CODE = "receiveProvinceCode";
	public static final String FIELD_RECEIVE_PROVINCE = "receiveProvince";
	public static final String FIELD_RECEIVE_CITY_CODE = "receiveCityCode";
	public static final String FIELD_RECEIVE_CITY = "receiveCity";
	public static final String FIELD_RECEIVE_DISTRICT_CODE = "receiveDistrictCode";
	public static final String FIELD_RECEIVE_DISTRICT = "receiveDistrict";
	public static final String FIELD_RECEIVE_TOWN = "receiveTown";
	public static final String FIELD_RECEIVE_ADDRESS = "receiveAddress";

	//
	// 业务方法(按public protected private顺序排列)
	// ------------------------------------------------------------------------------

	//
	// 数据库字段
	// ------------------------------------------------------------------------------


	@ApiModelProperty("主键id")
	@Id
	@GeneratedValue
	private Long id;
	@ApiModelProperty(value = "内部退款/退货单号")
	private String innerRefundNo;
	@ApiModelProperty(value = "内部订单号")
	private String innerOrderNo;
	@ApiModelProperty(value = "三方订单号")
	private String outerOrderNo;
	@ApiModelProperty(value = "三方退货单号")
	private String outerRefundNo;
	@ApiModelProperty(value = "退货人姓名")
	private String returnName;
	@ApiModelProperty(value = "退货人手机号码")
	private String mobile;
	@ApiModelProperty(value = "退货人省编码")
	private String provinceCode;
	@ApiModelProperty(value = "退货人所在省份")
	private String province;
	@ApiModelProperty(value = "退货人市编码")
	private String cityCode;
	@ApiModelProperty(value = "退货人所在市")
	private String city;
	@ApiModelProperty(value = "退货人区编码")
	private String districtCode;
	@ApiModelProperty(value = "退货人所在区")
	private String district;
	@ApiModelProperty(value = "退货人街道地址")
	private String town;
	@ApiModelProperty(value = "退货人详细地址")
	private String address;
	@ApiModelProperty(value = "退货人收件人姓名")
	private String receiveName;
	@ApiModelProperty(value = "退货人收件人手机号码")
	private String receiveMobile;
	@ApiModelProperty(value = "退货人收件人省编码")
	private String receiveProvinceCode;
	@ApiModelProperty(value = "退货人收件人所在省份")
	private String receiveProvince;
	@ApiModelProperty(value = "退货人收件人市编码")
	private String receiveCityCode;
	@ApiModelProperty(value = "退货人收件人所在市")
	private String receiveCity;
	@ApiModelProperty(value = "退货人收件人区编码")
	private String receiveDistrictCode;
	@ApiModelProperty(value = "退货人收件人所在区")
	private String receiveDistrict;
	@ApiModelProperty(value = "退货人收件人街道地址")
	private String receiveTown;
	@ApiModelProperty(value = "退货人收件人详细地址")
	private String receiveAddress;


}
