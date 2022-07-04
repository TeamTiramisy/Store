package com.dmdev.store.service;

import com.dmdev.store.database.repository.UserRepository;
import com.dmdev.store.dto.UserCreateDto;
import com.dmdev.store.dto.UserReadDto;
import com.dmdev.store.mapper.UserCreateMapper;
import com.dmdev.store.mapper.UserReadMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.dmdev.store.database.entity.BlackList.*;
import static com.dmdev.store.database.entity.Role.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserReadMapper mapper;
    private final UserCreateMapper createMapper;

    public List<UserReadDto> findAll(){
        return userRepository.findAll().stream()
                .map(mapper::map)
                .toList();
    }

    public Optional<UserReadDto> findById(Long id){
        return userRepository.findById(id)
                .map(mapper::map);
    }

    public Optional<UserReadDto> findByEmail(String email){
        return userRepository.findByEmail(email)
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

    @Transactional
    public UserReadDto create(UserCreateDto userCreateDto){
        return Optional.of(userCreateDto)
                .map(createMapper::map)
                .map(userRepository::save)
                .map(mapper::map)
                .orElseThrow();
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .map(user -> new User(
                        user.getEmail(),
                        user.getPassword(),
                        Collections.singleton(user.getRole())
                ))
                .orElseThrow(() -> new UsernameNotFoundException("Неверный логин или пороль"));
    }
}
