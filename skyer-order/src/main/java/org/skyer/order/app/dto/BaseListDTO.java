package org.skyer.order.app.dto;

import lombok.Data;

import java.util.List;

@Data
public class BaseListDTO<E,L> {
    private E entity;

    private List<L> list;
}
