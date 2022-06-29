package com.dmdev.store.mapper;

import com.dmdev.store.database.entity.User;
import com.dmdev.store.dto.UserCreateDto;
import org.springframework.stereotype.Component;

import static com.dmdev.store.database.entity.BlackList.*;
import static com.dmdev.store.database.entity.Role.*;

@Component
public class UserCreateMapper implements Mapper<UserCreateDto, User>{
    @Override
    public User map(UserCreateDto object) {
        return User.builder()
                .firstname(object.getFirstname())
                .lastname(object.getLastname())
                .email(object.getEmail())
                .password(object.getPassword())
                .tel(object.getTel())
                .address(object.getAddress())
                .role(USER)
                .gender(object.getGender())
                .blacklist(NO)
                .build();
    }
}
