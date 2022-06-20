package org.skyer.order.infra.feign.entity.select.logistics.postage.in;

import lombok.Data;

import java.util.List;

/**
 * [
 * {
 * "stockList": [
 * {
 * "number": 100,
 * "stockName": "测试1",
 * "stockId": 229186956499603456
 * }
 * ],
 * "sku": "CO00000008"
 * }
 * ]
 *
 * @author chenzz
 */
@Data
public class LogicWarehouseVO2 {

    /**
     * 仓库编码
     */
    private Long stockId;

    /**
     * 逻辑仓名称
     */
    private String stockName;

    /**
     * 逻辑仓编码
     */
    private String stockNo;

    /**
     * 地理位置
     */
    private Position position;

    /**
     * 寻源仓库接口返回逻辑仓
     */
    private List<SkuData> skuDataList;
}
