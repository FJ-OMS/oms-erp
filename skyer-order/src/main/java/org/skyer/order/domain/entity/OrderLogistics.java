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

import java.util.Date;

/**
 * 物流单
 *
 * @author wushaochuan 2022-01-18 14:34:55
 */
@ApiModel("物流单")
@VersionAudit
@ModifyAudit
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@Table(name = "sord_order_logistics")
public class OrderLogistics extends AuditDomain {

    public static final String FIELD_ID = "id";
    public static final String FIELD_OUTER_ORDER_NO = "outerOrderNo";
    public static final String FIELD_INNER_ORDER_NO = "innerOrderNo";
    public static final String FIELD_LOGISTICS_NO = "logisticsNo";
    public static final String FIELD_OUT_STOCK_NO = "outStockNo";
    public static final String FIELD_LOGISTICS_COMPANY = "logisticsCompany";
    public static final String FIELD_LOGISTICS_BUSINESS_NAME = "logisticsBusinessName";
    public static final String FIELD_LOGISTICS_STATUS = "logisticsStatus";
    public static final String FIELD_TYPE = "type";
    public static final String FIELD_RECEIVE_USER = "receiveUser";
    public static final String FIELD_RECEIVE_PHONE = "receivePhone";
    public static final String FIELD_RECEIVE_ADDRESS = "receiveAddress";
    public static final String FIELD_SEND_STOCK_ID = "sendStockId";
    public static final String FIELD_SEND_STOCK_NAME = "sendStockName";
    public static final String FIELD_SEND_USER = "sendUser";
    public static final String FIELD_SEND_PHONE = "sendPhone";
    public static final String FIELD_SEND_ADDRESS = "sendAddress";
    public static final String FIELD_SEND_TIME = "sendTime";

    //
    // 业务方法(按public protected private顺序排列)
    // ------------------------------------------------------------------------------

    //
    // 数据库字段
    // ------------------------------------------------------------------------------


    @ApiModelProperty("ID")
    @Id
    @GeneratedValue
    private Long id;
   @ApiModelProperty(value = "平台订单号")    
    private String outerOrderNo;
   @ApiModelProperty(value = "内部订单号")    
    private String innerOrderNo;
    @ApiModelProperty(value = "物流单号",required = true)
    @NotBlank
    private String logisticsNo;
   @ApiModelProperty(value = "出库结果订号")    
    private String outStockNo;
    @ApiModelProperty(value = "物流商",required = true)
    @NotBlank
    private String logisticsCompany;
    @ApiModelProperty(value = "物流业务名称",required = true)
    @NotBlank
    private String logisticsBusinessName;
   @ApiModelProperty(value = "物流状态")    
    private String logisticsStatus;
    @ApiModelProperty(value = "类型,1销售单,2售后单",required = true)
    @NotNull
    private Long type;
    @ApiModelProperty(value = "收货人",required = true)
    @NotBlank
    private String receiveUser;
    @ApiModelProperty(value = "收货人手机",required = true)
    @NotBlank
    private String receivePhone;
    @ApiModelProperty(value = "收货地址",required = true)
    @NotBlank
    private String receiveAddress;
    @ApiModelProperty(value = "发货仓",required = true)
    @NotNull
    private Long sendStockId;
    @ApiModelProperty(value = "发货仓名称",required = true)
    @NotBlank
    private String sendStockName;
    @ApiModelProperty(value = "寄件人",required = true)
    @NotBlank
    private String sendUser;
    @ApiModelProperty(value = "寄件人联系电话",required = true)
    @NotBlank
    private String sendPhone;
    @ApiModelProperty(value = "寄件人地址",required = true)
    @NotBlank
    private String sendAddress;
   @ApiModelProperty(value = "发货时间")    
    private Date sendTime;

	//
    // 非数据库字段
    // ------------------------------------------------------------------------------

    //
    // getter/setter
    // ------------------------------------------------------------------------------

    /**
     * @return ID
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
     * @return 物流单号
     */
	public String getLogisticsNo() {
		return logisticsNo;
	}

	public void setLogisticsNo(String logisticsNo) {
		this.logisticsNo = logisticsNo;
	}
    /**
     * @return 出库结果订号
     */
	public String getOutStockNo() {
		return outStockNo;
	}

	public void setOutStockNo(String outStockNo) {
		this.outStockNo = outStockNo;
	}
    /**
     * @return 物流商
     */
	public String getLogisticsCompany() {
		return logisticsCompany;
	}

	public void setLogisticsCompany(String logisticsCompany) {
		this.logisticsCompany = logisticsCompany;
	}
    /**
     * @return 物流业务名称
     */
	public String getLogisticsBusinessName() {
		return logisticsBusinessName;
	}

	public void setLogisticsBusinessName(String logisticsBusinessName) {
		this.logisticsBusinessName = logisticsBusinessName;
	}
    /**
     * @return 物流状态
     */
	public String getLogisticsStatus() {
		return logisticsStatus;
	}

	public void setLogisticsStatus(String logisticsStatus) {
		this.logisticsStatus = logisticsStatus;
	}
    /**
     * @return 类型,1销售单,2售后单
     */
	public Long getType() {
		return type;
	}

	public void setType(Long type) {
		this.type = type;
	}
    /**
     * @return 收货人
     */
	public String getReceiveUser() {
		return receiveUser;
	}

	public void setReceiveUser(String receiveUser) {
		this.receiveUser = receiveUser;
	}
    /**
     * @return 收货人手机
     */
	public String getReceivePhone() {
		return receivePhone;
	}

	public void setReceivePhone(String receivePhone) {
		this.receivePhone = receivePhone;
	}
    /**
     * @return 收货地址
     */
	public String getReceiveAddress() {
		return receiveAddress;
	}

	public void setReceiveAddress(String receiveAddress) {
		this.receiveAddress = receiveAddress;
	}
    /**
     * @return 发货仓
     */
	public Long getSendStockId() {
		return sendStockId;
	}

	public void setSendStockId(Long sendStockId) {
		this.sendStockId = sendStockId;
	}
    /**
     * @return 发货仓名称
     */
	public String getSendStockName() {
		return sendStockName;
	}

	public void setSendStockName(String sendStockName) {
		this.sendStockName = sendStockName;
	}
    /**
     * @return 寄件人
     */
	public String getSendUser() {
		return sendUser;
	}

	public void setSendUser(String sendUser) {
		this.sendUser = sendUser;
	}
    /**
     * @return 寄件人联系电话
     */
	public String getSendPhone() {
		return sendPhone;
	}

	public void setSendPhone(String sendPhone) {
		this.sendPhone = sendPhone;
	}
    /**
     * @return 寄件人地址
     */
	public String getSendAddress() {
		return sendAddress;
	}

	public void setSendAddress(String sendAddress) {
		this.sendAddress = sendAddress;
	}
    /**
     * @return 发货时间
     */
	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

}
