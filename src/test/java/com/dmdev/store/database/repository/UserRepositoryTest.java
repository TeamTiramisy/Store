package com.dmdev.store.database.repository;

import com.dmdev.store.annotation.IT;
import com.dmdev.store.database.entity.BlackList;
import com.dmdev.store.database.entity.Gender;
import com.dmdev.store.database.entity.Role;
import com.dmdev.store.database.entity.User;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static com.dmdev.store.database.entity.BlackList.*;
import static com.dmdev.store.database.entity.Gender.*;
import static com.dmdev.store.database.entity.Role.ADMIN;
import static org.junit.jupiter.api.Assertions.*;

@IT
@RequiredArgsConstructor
class UserRepositoryTest {

    private final UserRepository userRepository;

    @Test
    void findByEmailTest(){
        Optional<User> maybeUser = userRepository.findByEmail("rusya-niyazov@mail.ru");

        assertTrue(maybeUser.isPresent());

        maybeUser.ifPresent(user -> {
            assertEquals(1L, user.getId());
            assertEquals("Ruslan", user.getFirstname());
            assertEquals("Niyazov", user.getLastname());
            assertEquals("rusya-niyazov@mail.ru", user.getEmail());
            assertEquals("+375295857929", user.getTel());
            assertEquals("{noop}123", user.getPassword());
            assertEquals("Gavrilova 1 kv 6", user.getAddress());
            assertSame(ADMIN, user.getRole());
            assertSame(MALE, user.getGender());
            assertSame(NO, user.getBlacklist());
        });
    }

}