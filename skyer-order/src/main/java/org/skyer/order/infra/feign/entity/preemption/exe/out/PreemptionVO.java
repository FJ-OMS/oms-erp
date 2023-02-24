package org.skyer.order.infra.feign.entity.preemption.exe.out;

import lombok.Data;

import java.util.List;

@Data
public class PreemptionVO {

    private String skuCode;

    private List<Long> orderGoodsLineIds;
}
