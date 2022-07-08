package com.dmdev.store.service;

import com.dmdev.store.StoreTest;
import com.dmdev.store.annotation.IT;
import com.dmdev.store.dto.BasketCreateDto;
import com.dmdev.store.dto.BasketReadDto;
import com.dmdev.store.dto.TechnicReadDto;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@RequiredArgsConstructor
class BasketServiceTest extends StoreTest {

    private final BasketService basketService;

    @Test
    void createTest(){


        Optional<BasketReadDto> basket = basketService.create(3L, "rusya-niyazov@mail.ru");

        assertTrue(basket.isPresent());

        basket.ifPresent(basketReadDto -> {
            assertEquals(1L, basketReadDto.getUser().getId());
            assertEquals(3L, basketReadDto.getTechnic().getId());
        });

        assertTrue(basketService.create(6L, "ruslankarina1.2@gmail.com").isEmpty());
    }

    @Test
    void findByUserAndTechnicTest() {
        Optional<BasketReadDto> basketEmpty = basketService.findByUserAndTechnic(1L, 1L);
        Optional<BasketReadDto> basket = basketService.findByUserAndTechnic(5L, 6L);

        assertTrue(basketEmpty.isEmpty());
        assertTrue(basket.isPresent());

        basket.ifPresent(basketReadDto -> {
            assertEquals(5L, basketReadDto.getUser().getId());
            assertEquals(6L, basketReadDto.getTechnic().getId());
        });
    }

    @Test
    void findByUserIdBasketTest(){
        List<TechnicReadDto> technics = basketService.findByUserIdBasket("ruslankarina1.2@gmail.com");

        assertEquals(6, technics.size());

        List<String> list = technics.stream()
                .map(TechnicReadDto::getName).toList();

        assertTrue(list.contains("Samsung Galaxy M12 64GB"));
    }

    @Test
    void deleteTest(){
        assertTrue(basketService.delete("ruslankarina1.2@gmail.com", 6L));
        assertFalse(basketService.delete("ruslankarina1.2@gmail.com", 66L));
    }

    @Test
    void deleteByUserIdTest(){
        assertTrue(basketService.delete(5L));
        assertFalse(basketService.delete(66L));
    }

}