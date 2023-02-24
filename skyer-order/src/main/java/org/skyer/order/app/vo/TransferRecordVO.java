package org.skyer.order.app.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.skyer.order.app.dto.NeedOrderDTO;


import java.util.List;

@Data
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class TransferRecordVO {
    private Long id;
    private String code;
    private  Boolean flag;
    private List<NeedOrderDTO> needOrderDTOList;
}
