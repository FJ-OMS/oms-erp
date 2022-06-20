package org.skyer.order.infra.feign.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


@Data
public class SearchOrderDTO {

    private String innerCode;

    /**
     * 店铺Code
     */
    private String shopCode;

    /**
     * 支付时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date payTime;

    /**
     * 下单时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date orderTime;

    /**
     * 订单金额
     */
    private BigDecimal price;


    List<SearchGiftDTO> searchGiftDTOList;

}
