package com.dmdev.store.service;

import com.dmdev.store.annotation.IT;
import com.dmdev.store.database.entity.Role;
import com.dmdev.store.dto.UserReadDto;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@IT
@RequiredArgsConstructor
class UserServiceTest {

    private static final Long USER_ID = 1L;

    private final UserService userService;

    @Test
    void findAllTest(){
        List<UserReadDto> users = userService.findAll();

        assertEquals(5, users.size());

        Optional<UserReadDto> maybeUser = users.stream()
                .filter(user -> user.getEmail().equals("rusya-niyazov@mail.ru"))
                .findFirst();

        assertTrue(maybeUser.isPresent());

        maybeUser.ifPresent(user -> {
            assertEquals(USER_ID, user.getId());
            assertEquals("Ruslan", user.getFirstname());
            assertEquals("Niyazov", user.getLastname());
            assertEquals("rusya-niyazov@mail.ru", user.getEmail());
            assertEquals("123", user.getPassword());
            assertEquals("+375295857929", user.getTel());
            assertEquals("Gavrilova 1 kv 6", user.getAddress());
            assertEquals("ADMIN", user.getRole());
            assertEquals("MALE", user.getGender());
            assertEquals("NO", user.getBlacklist());
        });
    }

    @Test
    void findByIdTest(){
        Optional<UserReadDto> maybeUser = userService.findById(1L);

        assertTrue(maybeUser.isPresent());

        maybeUser.ifPresent(user -> {
            assertEquals(USER_ID, user.getId());
            assertEquals("Ruslan", user.getFirstname());
            assertEquals("Niyazov", user.getLastname());
            assertEquals("rusya-niyazov@mail.ru", user.getEmail());
            assertEquals("123", user.getPassword());
            assertEquals("+375295857929", user.getTel());
            assertEquals("Gavrilova 1 kv 6", user.getAddress());
            assertEquals("ADMIN", user.getRole());
            assertEquals("MALE", user.getGender());
            assertEquals("NO", user.getBlacklist());
        });
    }

    @Test
    void updateRoleTest(){
        Optional<UserReadDto> maybeUser = userService.updateRole(USER_ID);

        assertTrue(maybeUser.isPresent());

        maybeUser.ifPresent(user-> assertEquals("USER", user.getRole()));


    }

    @Test
    void updateBlacklistTest(){
        Optional<UserReadDto> maybeUser = userService.updateBlacklist(USER_ID);

        assertTrue(maybeUser.isPresent());

        maybeUser.ifPresent(user-> assertEquals("YES", user.getBlacklist()));
    }
}