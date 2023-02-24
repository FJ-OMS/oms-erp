package org.skyer.order.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NeedOrderDTO {

    private Long auditId;

    private String innerOrderNo;

    private List<Long> itemLineIdList;
}
