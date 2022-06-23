package com.dmdev.store.service;

import com.dmdev.store.database.entity.BlackList;
import com.dmdev.store.database.entity.Role;
import com.dmdev.store.database.entity.User;
import com.dmdev.store.database.repository.UserRepository;
import com.dmdev.store.dto.UserReadDto;
import com.dmdev.store.mapper.UserReadMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.dmdev.store.database.entity.BlackList.*;
import static com.dmdev.store.database.entity.Role.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final UserReadMapper mapper;

    public List<UserReadDto> findAll(){
        return userRepository.findAll().stream()
                .map(mapper::map)
                .toList();
    }

    public Optional<UserReadDto> findById(Long id){
        return userRepository.findById(id)
                .map(mapper::map);
    }

    @Transactional
    public Optional<UserReadDto> updateRole(Long id){
        return userRepository.findById(id).
                map(user -> {
                    if (user.getRole().equals(ADMIN)) {
                        user.setRole(USER);
                    } else {
                        user.setRole(ADMIN);
                    }
                    return user;
                })
                .map(userRepository::saveAndFlush)
                .map(mapper::map);
    }

    @Transactional
    public Optional<UserReadDto> updateBlacklist(Long id){
        return userRepository.findById(id).
                map(user -> {
                    if (user.getBlacklist().equals(NO)) {
                        user.setBlacklist(YES);
                    } else {
                        user.setBlacklist(NO);
                    }
                    return user;
                })
                .map(userRepository::saveAndFlush)
                .map(mapper::map);
    }
}
