package com.dmdev.store.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class BasketReadDto {

    Long id;

    UserReadDto user;

    TechnicReadDto technic;
}
