package org.skyer.goods.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.skyer.starter.keyencrypt.core.Encrypt;

/**
 * {
 * "levelThreeAreaCode": 110101001008,
 * "levelTwoAreaCode": 110101001000,
 * "levelOneAreaCode": 110101000000,
 * "levelThreeAreaName": "黄图岗社区居委会",
 * "levelTwoAreaName": "东华门街道",
 * "levelOneAreaName": "东城区"
 * }
 *
 * @description
 * @Author chenzz
 * @create 2021/8/23
 */
@Data
public class ChinaZoingRemoteVO {

    @ApiModelProperty("3级编码")
    @Encrypt
    private Long levelThreeAreaCode;

    @ApiModelProperty("2级编码")
    @Encrypt
    private Long levelTwoAreaCode;

    @ApiModelProperty("1级编码")
    @Encrypt
    private Long levelOneAreaCode;

    @ApiModelProperty("三级名称")
    private String levelThreeAreaName;

    @ApiModelProperty("二级名称")
    private String levelTwoAreaName;

    @ApiModelProperty("一级名称")
    private String levelOneAreaName;


}
