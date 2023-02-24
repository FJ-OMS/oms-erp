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

/**
 * 内部订单收货人信息
 *
 * @author wushaochuan 2022-01-18 14:34:53
 */
@ApiModel("内部订单收货人信息")
@ModifyAudit
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@Table(name = "sord_receiver_info")
public class ReceiverInfo extends AuditDomain {

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

    /**
     * @return 主键id
     */
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return 平台订单号
     */
    public String getOuterOrderNo() {
        return outerOrderNo;
    }

    public void setOuterOrderNo(String outerOrderNo) {
        this.outerOrderNo = outerOrderNo;
    }

    /**
     * @return 内部订单号
     */
    public String getInnerOrderNo() {
        return innerOrderNo;
    }

    public void setInnerOrderNo(String innerOrderNo) {
        this.innerOrderNo = innerOrderNo;
    }

    /**
     * @return 收货人姓名
     */
    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    /**
     * @return 收货人手机号码
     */
    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    /**
     * @return 收货人电话号码
     */
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * @return 收货人邮编
     */
    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    /**
     * @return 收货人国籍
     */
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * @return 省编码
     */
    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }

    /**
     * @return 收货人所在省份
     */
    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    /**
     * @return 市编码
     */
    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    /**
     * @return 收货人所在市
     */
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    /**
     * @return 区编码
     */
    public String getDistrictCode() {
        return districtCode;
    }

    public void setDistrictCode(String districtCode) {
        this.districtCode = districtCode;
    }

    /**
     * @return 收货人所在区
     */
    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    /**
     * @return 收货人街道地址
     */
    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    /**
     * @return 收货人详细地址
     */
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return 系统字段：删除标识
     */
    public Integer getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(Integer deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

}
