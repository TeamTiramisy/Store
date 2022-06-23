package com.dmdev.store.mapper;

import com.dmdev.store.database.entity.Technic;
import com.dmdev.store.dto.TechnicReadDto;
import org.springframework.stereotype.Component;

@Component
public class TechnicReadMapper implements Mapper<Technic, TechnicReadDto> {

    @Override
    public TechnicReadDto map(Technic object) {
        return TechnicReadDto.builder()
                .id(object.getId())
                .name(object.getName())
                .category(object.getCategory().name())
                .description(object.getDescription())
                .price(object.getPrice())
                .amount(object.getAmount())
                .image(object.getImage())
                .build();
    }
}
