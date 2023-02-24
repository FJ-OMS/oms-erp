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
 * 第三方售后退货人
 *
 * @author lzh 2022-04-20 16:30:30
 */
@ApiModel("第三方售后退货人")
@VersionAudit
@ModifyAudit
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@Table(name = "sord_third_party_after_receiver_info")
@Data
public class ThirdPartyAfterReceiverInfo extends AuditDomain {

	public static final String FIELD_ID = "id";
	public static final String FIELD_OUTER_ORDER_NO = "outerOrderNo";
	public static final String FIELD_OUT_REFUND_NO = "outerRefundNo";
	public static final String FIELD_RECEIVER_NAME = "receiverName";
	public static final String FIELD_MOBILE = "mobile";
	public static final String FIELD_PHONE = "phone";
	public static final String FIELD_ZIP_CODE = "zipCode";
	public static final String FIELD_COUNTRY = "country";
	public static final String FIELD_PROVINCE_CODE = "provinceCode";
	public static final String FIELD_PROVINCE = "province";
	public static final String FIELD_CITY_CODE = "cityCode";
	public static final String FIELD_CITY = "city";
	public static final String FIELD_DISTRICT_CODE = "districtCode";
	public static final String FIELD_DISTRICT = "district";
	public static final String FIELD_TOWN = "town";
	public static final String FIELD_ADDRESS = "address";
	public static final String FIELD_DELETE_FLAG = "deleteFlag";


	@ApiModelProperty("主键id")
	@Id
	@GeneratedValue
	private Long id;
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
