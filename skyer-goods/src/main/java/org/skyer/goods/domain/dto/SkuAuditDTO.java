package org.skyer.goods.domain.dto;

import lombok.Data;

import org.skyer.starter.keyencrypt.core.Encrypt;

@Data
public class SkuAuditDTO {

    @Encrypt
    private Long id;
}
