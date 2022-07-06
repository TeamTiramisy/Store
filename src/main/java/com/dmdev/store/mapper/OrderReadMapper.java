package com.dmdev.store.mapper;

import com.dmdev.store.database.entity.Order;
import com.dmdev.store.dto.OrderCreateDto;
import com.dmdev.store.dto.OrderReadDto;
import com.dmdev.store.dto.UserReadDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class OrderReadMapper implements Mapper<Order, OrderReadDto> {

    private final UserReadMapper mapper;

    @Override
    public OrderReadDto map(Order object) {
        UserReadDto userReadDto = Optional.ofNullable(object.getUser())
                .map(mapper::map)
                .orElse(null);

        return OrderReadDto.builder()
                .id(object.getId())
                .product(object.getProduct())
                .user(userReadDto)
                .dateRegistration(object.getDateRegistration().toString())
                .dateClose(object.getDateClose().toString())
                .status(object.getStatus().name())
                .total(object.getTotal())
                .build();
    }
}
