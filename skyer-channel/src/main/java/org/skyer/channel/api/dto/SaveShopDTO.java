package org.skyer.channel.api.dto;

import lombok.Data;

import org.skyer.channel.domain.entity.Shop;

@Data
public class SaveShopDTO {
    private Shop shop;
    private Boolean flag;
}
