package org.skyer.stock.api.dto;

import lombok.Data;

import org.skyer.starter.keyencrypt.core.Encrypt;

@Data
public class CodeAndIdDTO {
    private String code;
    @Encrypt
    private Long id;
}
