package org.skyer.stock.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NeedOrderDTO {

    private Long auditId;

    private String innerOrderNo;

    private List<Long> itemLineIdList;
}
