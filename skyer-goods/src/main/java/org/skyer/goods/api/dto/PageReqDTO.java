package org.skyer.goods.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.skyer.core.domain.Page;
import org.skyer.core.domain.PageInfo;

import java.io.Serializable;

/**
 * @description
 * @Author wusc
 * @create 2021/6/23 2:58 下午
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "分页请求类")
public class PageReqDTO implements Serializable {

    @ApiModelProperty(value = "每页条数", example = "10")
    private Integer size;

    @ApiModelProperty(value = "当前页数", example = "1")
    private Integer page;

    public PageInfo createPage() {
        int page = this.page == null || this.page < 0 ? 0: this.page;
        int size = this.size == null || this.size < 0 ? 10: this.size;
        return new PageInfo(page, size);
    }
}

