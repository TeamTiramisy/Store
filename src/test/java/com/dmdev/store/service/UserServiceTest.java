package com.dmdev.store.service;

import com.dmdev.store.StoreTest;
import com.dmdev.store.annotation.IT;
import com.dmdev.store.dto.UserCreateDto;
import com.dmdev.store.dto.UserReadDto;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.Optional;

import static com.dmdev.store.database.entity.Gender.*;
import static org.junit.jupiter.api.Assertions.*;

@RequiredArgsConstructor
class UserServiceTest extends StoreTest {

    private static final Long USER_ID = 1L;

    private final UserService userService;

    @Test
    void findAllTest() {
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
            assertEquals("{noop}123", user.getPassword());
            assertEquals("+375295857929", user.getTel());
            assertEquals("Gavrilova 1 kv 6", user.getAddress());
            assertEquals("ADMIN", user.getRole());
            assertEquals("MALE", user.getGender());
            assertEquals("NO", user.getBlacklist());
        });
    }

    @Test
    void findByIdTest() {
        Optional<UserReadDto> maybeUser = userService.findById(1L);

        assertTrue(maybeUser.isPresent());

        maybeUser.ifPresent(user -> {
            assertEquals(USER_ID, user.getId());
            assertEquals("Ruslan", user.getFirstname());
            assertEquals("Niyazov", user.getLastname());
            assertEquals("rusya-niyazov@mail.ru", user.getEmail());
            assertEquals("{noop}123", user.getPassword());
            assertEquals("+375295857929", user.getTel());
            assertEquals("Gavrilova 1 kv 6", user.getAddress());
            assertEquals("ADMIN", user.getRole());
            assertEquals("MALE", user.getGender());
            assertEquals("NO", user.getBlacklist());
        });
    }

    @Test
    void updateRoleTest() {
        Optional<UserReadDto> maybeUser = userService.updateRole(USER_ID);

        assertTrue(maybeUser.isPresent());

        maybeUser.ifPresent(user -> assertEquals("USER", user.getRole()));


    }

    @Test
    void updateBlacklistTest() {
        Optional<UserReadDto> maybeUser = userService.updateBlacklist(USER_ID);

        assertTrue(maybeUser.isPresent());

        maybeUser.ifPresent(user -> assertEquals("YES", user.getBlacklist()));
    }

    @Test
    void createTest() {
        UserCreateDto user = UserCreateDto.builder()
                .firstname("test")
                .lastname("test")
                .email("test@mail.ru")
                .rawPassword("123")
                .tel("+375298888888")
                .address("test")
                .gender(MALE)
                .build();

        UserReadDto userReadDto = userService.create(user);

        assertEquals(user.getFirstname(), userReadDto.getFirstname());
        assertEquals(user.getLastname(), userReadDto.getLastname());
        assertEquals(user.getEmail(), userReadDto.getEmail());
        assertEquals(user.getTel(), userReadDto.getTel());
        assertEquals(user.getAddress(), userReadDto.getAddress());
        assertEquals(user.getGender().name(), userReadDto.getGender());
    }

    @Test
    void loadUserByUsernameTest() {
        UserDetails user = userService.loadUserByUsername("rusya-niyazov@mail.ru");

        assertEquals("rusya-niyazov@mail.ru", user.getUsername());
        assertEquals("{noop}123", user.getPassword());
    }

    @Test
    void loadUserByUsernameExceptionTest() {
        assertThrows(UsernameNotFoundException.class,
                () -> userService.loadUserByUsername("test@mail.ru"));
    }

    @Test
    void updateTest() {
        UserCreateDto userCreate = UserCreateDto.builder()
                .firstname("test")
                .lastname("test")
                .email("test@mail.ru")
                .rawPassword("333")
                .tel("345653")
                .address("Lenina")
                .build();

        Optional<UserReadDto> maybeUser = userService.update("rusya-niyazov@mail.ru", userCreate);

        assertTrue(maybeUser.isPresent());

        maybeUser.ifPresent(user ->{
            assertEquals(userCreate.getFirstname(), user.getFirstname());
            assertEquals(userCreate.getLastname(), user.getLastname());
            assertEquals(userCreate.getEmail(), user.getEmail());
            assertEquals(userCreate.getRawPassword(), user.getPassword());
            assertEquals(userCreate.getTel(), user.getTel());
            assertEquals(userCreate.getAddress(), user.getAddress());
        });
    }

    @Test
    void deleteTest(){
        assertTrue(userService.delete("rusya-niyazov@mail.ru"));

        assertFalse(userService.delete("test@mail.ru"));
    }
}