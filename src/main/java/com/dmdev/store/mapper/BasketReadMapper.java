package com.dmdev.store.mapper;

import com.dmdev.store.database.entity.Basket;
import com.dmdev.store.database.repository.TechnicRepository;
import com.dmdev.store.database.repository.UserRepository;
import com.dmdev.store.dto.BasketReadDto;
import com.dmdev.store.dto.TechnicReadDto;
import com.dmdev.store.dto.UserReadDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class BasketReadMapper implements Mapper<Basket, BasketReadDto>{

    private final UserReadMapper userReadMapper;
    private final TechnicReadMapper technicReadMapper;

    @Override
    public BasketReadDto map(Basket object) {
        UserReadDto user = Optional.ofNullable(object.getUser())
                .map(userReadMapper::map)
                .orElse(null);

        TechnicReadDto technic = Optional.ofNullable(object.getTechnic())
                .map(technicReadMapper::map)
                .orElse(null);

        return BasketReadDto.builder()
                .user(user)
                .technic(technic)
                .build();
    }
}
