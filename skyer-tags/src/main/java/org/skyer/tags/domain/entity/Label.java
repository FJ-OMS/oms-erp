package org.skyer.tags.domain.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.skyer.mybatis.annotation.ModifyAudit;
import org.skyer.mybatis.domain.AuditDomain;
import org.skyer.starter.keyencrypt.core.Encrypt;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 *
 * @author xurongfa 2022-01-22 11:33:07
 */
@ApiModel("标签")
@ModifyAudit
@Table(name = "tags_label")
@Data
public class Label extends AuditDomain {

    public static final String FIELD_ID = "id";
    public static final String FIELD_LABEL_NAME = "labelName";
    public static final String FIELD_LABEL_TYPE = "labelType";
    public static final String FIELD_MODULE_CODE = "moduleCode";
    public static final String FIELD_LABEL_CODE = "labelCode";
    public static final String FIELD_STATUS = "status";
    public static final String FIELD_TEXT_CONTENT = "textContent";
    public static final String FIELD_TEXT_COLOR = "textColor";
    public static final String FIELD_SYSTEM_LABEL_FLAG = "systemLabelFlag";
    public static final String FIELD_CHANGE_FLAG = "changeFlag";

    //
    // 业务方法(按public protected private顺序排列)
    // ------------------------------------------------------------------------------

    //
    // 数据库字段
    // ------------------------------------------------------------------------------


    @ApiModelProperty("id")
    @Id
    @GeneratedValue
    @Encrypt
    private Long id;
   @ApiModelProperty(value = "标签名称")    
    private String labelName;
   @ApiModelProperty(value = "标签类型")    
    private String labelType;
   @ApiModelProperty(value = "模块")    
    private String moduleCode;
   @ApiModelProperty(value = "标签code")    
    private String labelCode;
   @ApiModelProperty(value = "状态 0禁用 1启用")
    private Boolean status;
   @ApiModelProperty(value = "标签文本内容")    
    private String textContent;
   @ApiModelProperty(value = "标签文本颜色")    
    private String textColor;
   @ApiModelProperty(value = "是否系统标签 0否 1是")
    private Boolean systemLabelFlag;
   @ApiModelProperty(value = "是否可挪移 0否 1是")
    private Boolean changeFlag;

	//
    // 非数据库字段
    // ------------------------------------------------------------------------------

}
