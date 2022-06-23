package com.dmdev.store.service;

import com.dmdev.store.annotation.IT;
import com.dmdev.store.database.entity.Category;
import com.dmdev.store.database.entity.Technic;
import com.dmdev.store.dto.TechnicReadDto;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.dmdev.store.database.entity.Category.*;
import static org.junit.jupiter.api.Assertions.*;

@IT
@RequiredArgsConstructor
class TechnicServiceTest {

    private final TechnicService technicService;
    private static final TechnicReadDto technicReadDto = TechnicReadDto.builder()
            .id(1L)
            .name("Apple iPhone 13 256GB")
            .category("PHONE")
            .description("Диагональ экрана\t6.1 ″ Оперативная память\t4 Гб Постоянная память\t" +
                    "256 Гб Версия операционной системы\tiOS 15 Дополнительный модуль камеры\t" +
                    "есть, сверхширокоугольный Разрешение камеры\t12 Мп Кол-во SIM-карт\t" +
                    "2 Емкость аккумулятора\t3240 мАч")
            .price(3999)
            .amount(6)
            .image("PHONE/iPhone13256.png")
            .build();

    @Test
    void findAllCategoryTest() {
        Set<String> category = technicService.findAllCategory();

        assertEquals(6, category.size());
        assertTrue(category.contains("PHONE"));
    }

    @Test
    void findAllByCategoryTest() {
        List<TechnicReadDto> phone = technicService.findAllByCategory(PHONE);

        assertEquals(17, phone.size());
        assertTrue((phone.stream().map(TechnicReadDto::getName).toList()).contains("Apple iPhone 13 256GB"));
    }

    @Test
    void findByIdTest() {
        Optional<TechnicReadDto> product = technicService.findById(1L);
        assertTrue(product.isPresent());

        product.ifPresent(technic -> assertEquals(technicReadDto, technic));
    }

    @Test
    void findByNameContainingIgnoreCaseTest() {
        List<TechnicReadDto> phones = technicService.findByNameContainingIgnoreCase("phone");
        Optional<TechnicReadDto> product = phones.stream().filter(phone -> phone.getId() == 1L).findFirst();

        assertEquals(5, phones.size());
        assertTrue(product.isPresent());

        product.ifPresent(technic -> {
            assertEquals(technicReadDto.getId(), technic.getId());
            assertEquals(technicReadDto.getName(), technic.getName());
            assertEquals(technicReadDto.getCategory(), technic.getCategory());
            assertEquals(technicReadDto.getDescription(), technic.getDescription());
            assertEquals(technicReadDto.getPrice(), technic.getPrice());
            assertEquals(technicReadDto.getAmount(), technic.getAmount());
            assertEquals(technicReadDto.getImage(), technic.getImage());
        });


    }

    @Test
    void findAvatarTest() {
        Optional<byte[]> avatar = technicService.findAvatar(1L);
        assertTrue(avatar.isPresent());
    }

}