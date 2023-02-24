package org.skyer.stock.api.dto;

import lombok.Data;

import java.util.List;

@Data
public class AdjustSagaDTO {
    private Long adjustId;
    private String adjustCode;
    private ExeAllocationDTO exeAllocationDTO;
    private List<NeedOrderDTO> needOrderDTOList;
}
