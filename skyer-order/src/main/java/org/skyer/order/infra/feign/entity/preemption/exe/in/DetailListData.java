package org.skyer.order.infra.feign.entity.preemption.exe.in;

import lombok.Data;

import java.util.List;

@Data
public class DetailListData {
    private List<Long> orderGoodsLineIds;

    private String skuCode;

    private Integer orderQty;
}
