package com.dmdev.store.database.repository;

import com.dmdev.store.annotation.IT;
import com.dmdev.store.database.entity.Basket;
import com.dmdev.store.database.entity.Technic;
import com.dmdev.store.database.entity.User;
import com.dmdev.store.dto.BasketReadDto;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@IT
@RequiredArgsConstructor
class BasketRepositoryTest {

    private final BasketRepository basketRepository;
    private final UserRepository userRepository;
    private final TechnicRepository technicRepository;

    @Test
    void findByUserAndTechnicTest() {
        Optional<User> user = userRepository.findById(5L);
        Optional<Technic> technic = technicRepository.findById(6L);

        if (user.isPresent() && technic.isPresent()) {
            Optional<Basket> maybeBasket = basketRepository.findByUserAndTechnic(user.orElseThrow(),
                    technic.orElseThrow());

            maybeBasket.ifPresent(basketReadDto -> {
                assertEquals(5L, basketReadDto.getUser().getId());
                assertEquals(6L, basketReadDto.getTechnic().getId());
            });
        }
    }

    @Test
    void deleteAllByUserTest(){
        Optional<User> maybeUser = userRepository.findById(5L);

        maybeUser.ifPresent(basketRepository::deleteAllByUser);

        assertTrue(basketRepository.findById(1L).isEmpty());
        assertTrue(basketRepository.findById(2L).isEmpty());
        assertTrue(basketRepository.findById(3L).isEmpty());
        assertTrue(basketRepository.findById(4L).isEmpty());
        assertTrue(basketRepository.findById(5L).isEmpty());


    }
}