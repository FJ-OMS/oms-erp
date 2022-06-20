package org.skyer.goods.domain.dto;

import lombok.Data;

/**
 * @description
 * @Author wusc
 * @create 2021/6/28 10:04 上午
 */
@Data
public class TotalCategoryDTO {
    /**
     * 分类级别
     */
    private Integer level;
    /**
     * 数量
      */
    private Integer num;
}
