package com.dmdev.store.database.repository;

import com.dmdev.store.StoreTest;
import com.dmdev.store.annotation.IT;
import com.dmdev.store.database.entity.*;
import com.dmdev.store.dto.OrderReadDto;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDate;
import java.util.List;

import static com.dmdev.store.database.entity.BlackList.*;
import static com.dmdev.store.database.entity.Gender.*;
import static com.dmdev.store.database.entity.Role.ADMIN;
import static org.junit.jupiter.api.Assertions.*;

@RequiredArgsConstructor
class OrderRepositoryTest extends StoreTest {

    private final OrderRepository orderRepository;

    private static final User USER = User.builder()
            .id(1L)
            .firstname("Ruslan")
            .lastname("Niyazov")
            .email("rusya-niyazov@mail.ru")
            .password("{noop}123")
            .tel("+375295857929")
            .address("Gavrilova 1 kv 6")
            .role(ADMIN)
            .gender(MALE)
            .blacklist(NO)
            .build();

    @Test
    void findAllByUserTest() {
        List<Order> orders = orderRepository.findAllByUser(USER);

        assertEquals(6, orders.size());

        orderRepository.findById(1L).ifPresent(order -> assertTrue(orders.contains(order)));
    }

    @Test
    void findAllByProductContainingTest() {
        List<Order> orders = orderRepository.findAllByProductContaining("Sony PlayStation 5");

        assertEquals(2, orders.size());

        boolean contains = orders.stream()
                .map(Order::getTotal)
                .toList()
                .contains(7499);

        assertTrue(contains);
    }

    @ParameterizedTest
    @ValueSource(longs = {5L, 6L, 7L})
    void deleteDateCloseTest(Long id) {
        orderRepository.deleteDateClose(LocalDate.now());

        assertTrue(orderRepository.findById(id).isEmpty());
    }

    @Test
    void findAllByStatusByUserIdTest() {
        List<Order> orders = orderRepository.findAllByStatusByUserId(USER);

        assertEquals(2, orders.size());

        boolean contains = orders.stream()
                .map(Order::getProduct)
                .toList()
                .contains("Apple MacBook Air 13 M1 2020 256GB / MGN63 - 1");

        assertTrue(contains);
    }
}