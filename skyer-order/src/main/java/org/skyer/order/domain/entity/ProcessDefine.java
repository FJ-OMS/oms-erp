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
import org.skyer.starter.keyencrypt.core.Encrypt;

import com.mysql.jdbc.Blob;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 
 *
 * @author linzhuanghuang@ebc.com 2022-01-14 17:53:17
 */
@ApiModel("流程定义")
@ModifyAudit
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@Table(name = "sord_process_define")
@Data
public class ProcessDefine extends AuditDomain {

    public static final String FIELD_ID = "id";
    public static final String FIELD_PROCESS_NAME = "processName";
    public static final String FIELD_PROCESS_BPMN = "processBpmn";

    //
    // 业务方法(按public protected private顺序排列)
    // ------------------------------------------------------------------------------

    //
    // 数据库字段
    // ------------------------------------------------------------------------------


    @ApiModelProperty("ID")
    @Id
    @GeneratedValue
    @Encrypt
    private Long id;
    @ApiModelProperty(value = "流程名称")
    private String processName;
    @ApiModelProperty(value = "流程定义文件")
    private byte[] processBpmn;

    @ApiModelProperty("状态")
    private Integer status;

    @ApiModelProperty("流程定义id")
    private String processDefineId;

    @ApiModelProperty("流程文件名称")
    private String processFileName;

    @ApiModelProperty(value = "实际流程生成id")
    private String processId;
}
