package org.skyer.stock.api.dto;

import lombok.Data;

import java.util.List;

@Data
public class ExeAllocationDTO {
    private List<ReplenishmentPreemptionDTO> preemptionList;
}
