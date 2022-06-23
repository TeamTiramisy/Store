package com.dmdev.store.mapper;

import com.dmdev.store.database.entity.User;
import com.dmdev.store.dto.UserReadDto;
import org.springframework.stereotype.Component;

@Component
public class UserReadMapper implements Mapper<User, UserReadDto> {


    @Override
    public UserReadDto map(User object) {
        return UserReadDto.builder()
                .id(object.getId())
                .firstname(object.getFirstname())
                .lastname(object.getLastname())
                .email(object.getEmail())
                .password(object.getPassword())
                .tel(object.getTel())
                .address(object.getAddress())
                .role(object.getRole().name())
                .gender(object.getGender().name())
                .blacklist(object.getBlacklist().name())
                .build();
    }

}

