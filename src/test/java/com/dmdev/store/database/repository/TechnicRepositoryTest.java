package com.dmdev.store.database.repository;

import com.dmdev.store.annotation.IT;
import com.dmdev.store.database.entity.Category;
import com.dmdev.store.database.entity.Technic;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.dmdev.store.database.entity.Category.PHONE;
import static org.junit.jupiter.api.Assertions.*;

@IT
@RequiredArgsConstructor
class TechnicRepositoryTest {

    private final TechnicRepository technicRepository;

    private static final Technic technic = Technic.builder()
            .id(1L)
            .name("Apple iPhone 13 256GB")
            .category(Category.PHONE)
            .description("Диагональ экрана\t6.1 ″ Оперативная память\t4 Гб Постоянная память\t" +
                    "256 Гб Версия операционной системы\tiOS 15 Дополнительный модуль камеры\t" +
                    "есть, сверхширокоугольный Разрешение камеры\t12 Мп Кол-во SIM-карт\t" +
                    "2 Емкость аккумулятора\t3240 мАч")
            .price(3999)
            .amount(6)
            .image("PHONE/iPhone13256.png")
            .build();

    @Test
    void findAllByCategoryTest(){
        List<Technic> phone = technicRepository.findByCategory(PHONE);

        assertEquals(17, phone.size());

        assertTrue(phone.contains(technic));
    }

    @Test
    void findByNameContainingIgnoreCaseTest(){
        List<Technic> phone = technicRepository.findByNameContainingIgnoreCase("phone");

        assertEquals(5, phone.size());

        assertTrue(phone.contains(technic));
    }

}