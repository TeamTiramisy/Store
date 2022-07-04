package com.dmdev.store.database.repository;

import com.dmdev.store.annotation.IT;
import com.dmdev.store.database.entity.Category;
import com.dmdev.store.database.entity.Technic;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static com.dmdev.store.database.entity.Category.PHONE;
import static org.junit.jupiter.api.Assertions.*;

@IT
@RequiredArgsConstructor
class TechnicRepositoryTest {

    private final TechnicRepository technicRepository;

    private static final Technic TECHNIC = Technic.builder()
            .id(66L)
            .name("Apple iPhone 13 256GB")
            .category(Category.PHONE)
            .description("diagonal\t6.1 RAM\t4GB SSD\t256GB battery\t3240 mAh")
            .price(3999)
            .amount(6)
            .image("PHONE/iPhone13256.png")
            .build();

    @Test
    void findAllByCategoryTest(){
        List<Technic> phone = technicRepository.findByCategory(PHONE);

        assertEquals(17, phone.size());

        assertTrue(phone.contains(TECHNIC));
    }

    @Test
    void findByNameContainingIgnoreCaseTest(){
        List<Technic> phone = technicRepository.findByNameContainingIgnoreCase("phone");

        assertEquals(5, phone.size());

        assertTrue(phone.contains(TECHNIC));
    }

    @Test
    void findByUserIdBasketTest(){
        List<Technic> technics = technicRepository.findByUserIdBasket(5L);

        assertEquals(6, technics.size());

        List<String> list = technics.stream()
                .map(Technic::getName).toList();

        assertTrue(list.contains("Samsung Galaxy M12 64GB"));
    }

}