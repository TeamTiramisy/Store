package com.dmdev.store.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class BasketCreateDto {

    Long usersId;
    Long technicId;
}
