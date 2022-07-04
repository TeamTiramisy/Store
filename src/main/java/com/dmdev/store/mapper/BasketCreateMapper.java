package com.dmdev.store.mapper;

import com.dmdev.store.database.entity.Basket;
import com.dmdev.store.database.entity.Technic;
import com.dmdev.store.database.entity.User;
import com.dmdev.store.database.repository.TechnicRepository;
import com.dmdev.store.database.repository.UserRepository;
import com.dmdev.store.dto.BasketCreateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class BasketCreateMapper implements Mapper<BasketCreateDto, Basket>{

    private final UserRepository userRepository;
    private final TechnicRepository technicRepository;

    @Override
    public Basket map(BasketCreateDto object) {
        return Basket.builder()
                .user(getUser(object.getUsersId()))
                .technic(getTechnic(object.getTechnicId()))
                .build();
    }

    public User getUser(Long userId){
        return Optional.ofNullable(userId)
                .flatMap(userRepository::findById)
                .orElse(null);
    }

    public Technic getTechnic(Long technicId){
        return Optional.ofNullable(technicId)
                .flatMap(technicRepository::findById)
                .orElse(null);
    }
}
