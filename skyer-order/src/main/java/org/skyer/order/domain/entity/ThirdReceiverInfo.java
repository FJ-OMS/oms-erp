package org.skyer.order.domain.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.validation.constraints.NotBlank;

import lombok.Data;
import org.skyer.mybatis.domain.AuditDomain;
import org.skyer.mybatis.annotation.ModifyAudit;
import org.skyer.mybatis.annotation.VersionAudit;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 内部订单收货人信息
 *
 * @author zhanglongbing@ebc.com 2022-04-20 10:15:57
 */
@ApiModel("内部订单收货人信息")
@VersionAudit
@ModifyAudit
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@Table(name = "sord_third_receiver_info")
@Data
public class ThirdReceiverInfo extends AuditDomain {

    public static final String FIELD_ID = "id";
    public static final String FIELD_OUTER_ORDER_NO = "outerOrderNo";
    public static final String FIELD_INNER_ORDER_NO = "innerOrderNo";
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
   @ApiModelProperty(value = "平台订单号")    
    private String outerOrderNo;
   @ApiModelProperty(value = "内部订单号")    
    private String innerOrderNo;
   @ApiModelProperty(value = "收货人姓名")    
    private String receiverName;
   @ApiModelProperty(value = "收货人手机号码")    
    private String mobile;
   @ApiModelProperty(value = "收货人电话号码")    
    private String phone;
   @ApiModelProperty(value = "收货人邮编")    
    private String zipCode;
   @ApiModelProperty(value = "收货人国籍")    
    private String country;
   @ApiModelProperty(value = "省编码")    
    private String provinceCode;
   @ApiModelProperty(value = "收货人所在省份")    
    private String province;
   @ApiModelProperty(value = "市编码")    
    private String cityCode;
   @ApiModelProperty(value = "收货人所在市")    
    private String city;
   @ApiModelProperty(value = "区编码")    
    private String districtCode;
   @ApiModelProperty(value = "收货人所在区")    
    private String district;
   @ApiModelProperty(value = "收货人街道地址")    
    private String town;
   @ApiModelProperty(value = "收货人详细地址")    
    private String address;
   @ApiModelProperty(value = "系统字段：删除标识")    
    private Integer deleteFlag;

	//
    // 非数据库字段
    // ------------------------------------------------------------------------------

    //
    // getter/setter
    // ------------------------------------------------------------------------------



}
