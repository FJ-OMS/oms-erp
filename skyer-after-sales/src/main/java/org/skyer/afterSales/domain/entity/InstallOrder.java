package org.skyer.afterSales.domain.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.skyer.mybatis.annotation.ModifyAudit;
import org.skyer.mybatis.annotation.VersionAudit;
import org.skyer.mybatis.domain.AuditDomain;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 安装工单表
 *
 * @author chenzz 2021-09-13 14:46:41
 */
@ApiModel("安装工单表")
@VersionAudit
@ModifyAudit
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@Table(name = "safs_install_order")
public class InstallOrder extends AuditDomain {

    public static final String FIELD_ID = "id";
    public static final String FIELD_INSTALL_ORDER_NO = "installOrderNo";
    public static final String FIELD_STATUS = "status";
    public static final String FIELD_INNER_ORDER_NO = "innerOrderNo";
    public static final String FIELD_OUTER_ORDER_NO = "outerOrderNo";
    public static final String FIELD_OUT_STOCK_NOTICE_NO = "outStockNoticeNo";
    public static final String FIELD_SHOP_NAME = "shopName";
    public static final String FIELD_CHANNEL_NAME = "channelName";
    public static final String FIELD_CUSTOMER_NAME = "customerName";
    public static final String FIELD_CUSTOMER_PHONE = "customerPhone";
    public static final String FIELD_CUSTOMER_ADDRESS = "customerAddress";
    public static final String FIELD_DELETE_FLAG = "deleteFlag";
    public static final String FIELD_TENANT_ID = "tenantId";

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
    @ApiModelProperty(value = "安装工单单号", required = true)
    @NotBlank
    private String installOrderNo;
    @ApiModelProperty(value = "安装状态,1待安装,2取消安装,3安装完成", required = true)
    @NotNull
    private Integer status;
    @ApiModelProperty(value = "内部订单号", required = true)
    @NotBlank
    private String innerOrderNo;
    @ApiModelProperty(value = "外部第三方订单号", required = true)
    @NotBlank
    private String outerOrderNo;
    @ApiModelProperty(value = "出库通知单单号", required = true)
    @NotBlank
    private String outStockNoticeNo;
    @ApiModelProperty(value = "店铺名称", required = true)
    @NotBlank
    private String shopName;
    @ApiModelProperty(value = "渠道名称", required = true)
    @NotBlank
    private String channelName;
    @ApiModelProperty(value = "客户姓名", required = true)
    @NotBlank
    private String customerName;
    @ApiModelProperty(value = "客户联系方式", required = true)
    @NotBlank
    private String customerPhone;
    @ApiModelProperty(value = "客户联系地址", required = true)
    @NotBlank
    private String customerAddress;
    @ApiModelProperty(value = "删除状态")
    private Boolean deleteFlag;
    @ApiModelProperty(value = "租户Id")
    private Long tenantId;

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
     * @return 安装工单单号
     */
    public String getInstallOrderNo() {
        return installOrderNo;
    }

    public void setInstallOrderNo(String installOrderNo) {
        this.installOrderNo = installOrderNo;
    }

    /**
     * @return 安装状态, 1待安装, 2取消安装, 3安装完成
     */
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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
     * @return 外部第三方订单号
     */
    public String getOuterOrderNo() {
        return outerOrderNo;
    }

    public void setOuterOrderNo(String outerOrderNo) {
        this.outerOrderNo = outerOrderNo;
    }

    /**
     * @return 出库通知单单号
     */
    public String getOutStockNoticeNo() {
        return outStockNoticeNo;
    }

    public void setOutStockNoticeNo(String outStockNoticeNo) {
        this.outStockNoticeNo = outStockNoticeNo;
    }

    /**
     * @return 店铺名称
     */
    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    /**
     * @return 渠道名称
     */
    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    /**
     * @return 客户姓名
     */
    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /**
     * @return 客户联系方式
     */
    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    /**
     * @return 客户联系地址
     */
    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    /**
     * @return 删除状态
     */
    public Boolean getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(Boolean deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    /**
     * @return 租户Id
     */
    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

}
