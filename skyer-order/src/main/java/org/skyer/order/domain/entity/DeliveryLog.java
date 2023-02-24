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
 * 订单发货日志
 *
 * @author wushaochuan 2022-01-18 14:34:54
 */
@ApiModel("订单发货日志")
@VersionAudit
@ModifyAudit
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@Table(name = "sord_delivery_log")
public class DeliveryLog extends AuditDomain {

    public static final String FIELD_ID = "id";
    public static final String FIELD_OUTER_ORDER_NO = "outerOrderNo";
    public static final String FIELD_INNER_ORDER_NO = "innerOrderNo";
    public static final String FIELD_TYPE = "type";
    public static final String FIELD_NOTICE_DOC = "noticeDoc";
    public static final String FIELD_RESULT_DOC = "resultDoc";
    public static final String FIELD_STATUS = "status";
    public static final String FIELD_RETRY_NUM = "retryNum";

    //
    // 业务方法(按public protected private顺序排列)
    // ------------------------------------------------------------------------------

    //
    // 数据库字段
    // ------------------------------------------------------------------------------


    @ApiModelProperty("")
    @Id
    @GeneratedValue
    private Long id;
   @ApiModelProperty(value = "平台订单号")    
    private String outerOrderNo;
   @ApiModelProperty(value = "内部订单号")    
    private String innerOrderNo;
   @ApiModelProperty(value = "通知类型：发货、收货")    
    private Integer type;
   @ApiModelProperty(value = "通知单请求内容")    
    private String noticeDoc;
   @ApiModelProperty(value = "结果单响应内容")    
    private String resultDoc;
   @ApiModelProperty(value = "请求结果状态位：0-失败，1-成功")    
    private Integer status;
   @ApiModelProperty(value = "重试次数，默认值为0")    
    private Long retryNum;

	//
    // 非数据库字段
    // ------------------------------------------------------------------------------

    //
    // getter/setter
    // ------------------------------------------------------------------------------

    /**
     * @return 
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
     * @return 通知类型：发货、收货
     */
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
    /**
     * @return 通知单请求内容
     */
	public String getNoticeDoc() {
		return noticeDoc;
	}

	public void setNoticeDoc(String noticeDoc) {
		this.noticeDoc = noticeDoc;
	}
    /**
     * @return 结果单响应内容
     */
	public String getResultDoc() {
		return resultDoc;
	}

	public void setResultDoc(String resultDoc) {
		this.resultDoc = resultDoc;
	}
    /**
     * @return 请求结果状态位：0-失败，1-成功
     */
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
    /**
     * @return 重试次数，默认值为0
     */
	public Long getRetryNum() {
		return retryNum;
	}

	public void setRetryNum(Long retryNum) {
		this.retryNum = retryNum;
	}

}
