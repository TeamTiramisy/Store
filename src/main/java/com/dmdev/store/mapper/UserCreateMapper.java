package com.dmdev.store.mapper;

import com.dmdev.store.database.entity.User;
import com.dmdev.store.dto.UserCreateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Optional;

import static com.dmdev.store.database.entity.BlackList.*;
import static com.dmdev.store.database.entity.Role.*;

@Component
@RequiredArgsConstructor
public class UserCreateMapper implements Mapper<UserCreateDto, User>{

    private final PasswordEncoder passwordEncoder;

    @Override
    public User map(UserCreateDto object) {
        User user = User.builder()
                .firstname(object.getFirstname())
                .lastname(object.getLastname())
                .email(object.getEmail())
                .tel(object.getTel())
                .address(object.getAddress())
                .role(USER)
                .gender(object.getGender())
                .blacklist(NO)
                .build();

        Optional.ofNullable(object.getRawPassword())
                .filter(StringUtils::hasText)
                .map(passwordEncoder::encode)
                .ifPresent(user::setPassword);

        return user;
    }

    public User copy(User user, UserCreateDto userCreateDto){
        user.setFirstname(userCreateDto.getFirstname());
        user.setLastname(userCreateDto.getLastname());
        user.setEmail(userCreateDto.getEmail());
        user.setPassword(userCreateDto.getRawPassword());
        user.setTel(userCreateDto.getTel());
        user.setAddress(userCreateDto.getAddress());

        return user;
    }
}
