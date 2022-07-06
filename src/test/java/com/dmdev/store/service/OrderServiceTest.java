package com.dmdev.store.service;

import com.dmdev.store.annotation.IT;
import com.dmdev.store.database.entity.Order;
import com.dmdev.store.database.entity.Status;
import com.dmdev.store.database.repository.OrderRepository;
import com.dmdev.store.dto.OrderReadDto;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static com.dmdev.store.database.entity.Status.*;
import static org.junit.jupiter.api.Assertions.*;

@IT
@RequiredArgsConstructor
class OrderServiceTest {

    private final OrderService orderService;
    private final OrderRepository orderRepository;
    private static final String PRODUCT = "Samsung Galaxy M12 64GB - 1 , LG GA-B419SLGL - 1 , " +
            "Bosch KGV39XL2AR - 1 , Beko CSMV5335MC0S - 1 , MultiOffice 6A95ED4S12IV5 - 1 , " +
            "MultiGame 3R12D8H1G103V5 - 1";

    @Test
    void saveTest(){
        Integer[] amounts = {1, 1, 1, 1, 1, 1};
        OrderReadDto order = orderService.create("ruslankarina1.2@gmail.com", amounts);

        assertEquals(PRODUCT, order.getProduct());
        assertEquals(5L, order.getUser().getId());
        assertEquals(LocalDate.now().toString(), order.getDateRegistration());
        assertEquals("1900-01-01", order.getDateClose());
        assertEquals("PROCESSING", order.getStatus());
        assertEquals(7906, order.getTotal());
    }

    @Test
    void findAllByUserIdTest(){
        List<OrderReadDto> orders = orderService.findAllByUserId("rusya-niyazov@mail.ru");

        assertEquals(6, orders.size());

        boolean contains = orders.stream()
                .map(OrderReadDto::getProduct)
                .toList()
                .contains("LG 43UP76506LD - 2");

        assertTrue(contains);
    }

    @Test
    void deleteTest(){
        assertTrue(orderService.delete(1L));
        assertFalse(orderService.delete(100L));
    }

    @Test
    void findAllByProductTest(){
        List<OrderReadDto> orders = orderService.findAllByProduct("Sony PlayStation 5");

        assertEquals(2, orders.size());

        boolean contains = orders.stream()
                .map(OrderReadDto::getTotal)
                .toList()
                .contains(7499);

        assertTrue(contains);
    }

    @Test
    void findAllTest(){
        List<OrderReadDto> orders = orderService.findAll();

        assertEquals(9, orders.size());

        boolean contains = orders.stream()
                .map(OrderReadDto::getTotal)
                .toList()
                .contains(7499);

        assertTrue(contains);
    }

    @ParameterizedTest
    @ValueSource(longs = {5L, 6L, 7L})
    void deleteDateCloseTest(Long id){
        orderService.deleteDateClose(LocalDate.now());

        assertTrue(orderRepository.findById(id).isEmpty());
    }

    @Test
    void updateAcceptTest(){
        orderService.updateAccept(1L)
                .ifPresent(order -> assertEquals(ACCEPTED.name(), order.getStatus()));

    }

    @Test
    void updateRejectTest(){
        orderService.updateReject(1L)
                .ifPresent(order -> assertEquals(REJECTED.name(), order.getStatus()));

    }

    @Test
    void updateCompletedTest(){
        orderService.updateCompleted(8L)
                .ifPresent(order -> {
                    assertEquals(COMPLETED.name(), order.getStatus());
                    assertEquals(LocalDate.now().toString(), order.getDateClose());
                });

    }

    @Test
    void findAllByStatusByUserIdTest(){
        List<OrderReadDto> orders = orderService.findAllByStatusByUserId("rusya-niyazov@mail.ru");

        assertEquals(2, orders.size());

        boolean contains = orders.stream()
                .map(OrderReadDto::getProduct)
                .toList()
                .contains("Apple MacBook Air 13 M1 2020 256GB / MGN63 - 1");

        assertTrue(contains);
    }

}