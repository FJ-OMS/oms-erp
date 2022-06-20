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
 * 第三方平台订单
 *
 * @author wushaochuan 2022-01-18 14:34:55
 */
@ApiModel("第三方平台订单")
@ModifyAudit
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@Table(name = "sord_third_party_order")
public class ThirdPartyOrder extends AuditDomain {

    public static final String FIELD_ID = "id";
    public static final String FIELD_OUTER_ORDER_NO = "outerOrderNo";
    public static final String FIELD_INNER_ORDER_NO = "innerOrderNo";
    public static final String FIELD_SHOP_ID = "shopId";
    public static final String FIELD_CHANNEL = "channel";
    public static final String FIELD_FLOW_STATUS = "flowStatus";
    public static final String FIELD_REMARK = "remark";
    public static final String FIELD_BACK_WRITE_STATUS = "backWriteStatus";
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
    @ApiModelProperty(value = "店铺ID",required = true)
    @NotNull
    private Long shopId;
    @ApiModelProperty(value = "渠道",required = true)
    @NotBlank
    private String channel;
   @ApiModelProperty(value = "1-正常 0-暂停")    
    private Integer flowStatus;
   @ApiModelProperty(value = "备注")    
    private String remark;
   @ApiModelProperty(value = "回写状态")    
    private Integer backWriteStatus;
   @ApiModelProperty(value = "删除状态")    
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
     * @return 店铺ID
     */
	public Long getShopId() {
		return shopId;
	}

	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}
    /**
     * @return 渠道
     */
	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}
    /**
     * @return 1-正常 0-暂停
     */
	public Integer getFlowStatus() {
		return flowStatus;
	}

	public void setFlowStatus(Integer flowStatus) {
		this.flowStatus = flowStatus;
	}
    /**
     * @return 备注
     */
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
    /**
     * @return 回写状态
     */
	public Integer getBackWriteStatus() {
		return backWriteStatus;
	}

	public void setBackWriteStatus(Integer backWriteStatus) {
		this.backWriteStatus = backWriteStatus;
	}
    /**
     * @return 删除状态
     */
	public Integer getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(Integer deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

}
