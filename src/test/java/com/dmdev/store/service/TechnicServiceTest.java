package com.dmdev.store.service;

import com.dmdev.store.StoreTest;
import com.dmdev.store.annotation.IT;
import com.dmdev.store.database.entity.Category;
import com.dmdev.store.database.entity.Technic;
import com.dmdev.store.dto.TechnicCreateDto;
import com.dmdev.store.dto.TechnicReadDto;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.dmdev.store.database.entity.Category.*;
import static org.junit.jupiter.api.Assertions.*;

@RequiredArgsConstructor
class TechnicServiceTest extends StoreTest {

    private final TechnicService technicService;
    private static final TechnicReadDto technicReadDto = TechnicReadDto.builder()
            .id(66L)
            .name("Apple iPhone 13 256GB")
            .category("PHONE")
            .description("diagonal\t6.1 RAM\t4GB SSD\t256GB battery\t3240 mAh")
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
        Optional<TechnicReadDto> product = technicService.findById(66L);
        assertTrue(product.isPresent());

        product.ifPresent(technic -> assertEquals(technicReadDto, technic));
    }

    @Test
    void findByNameContainingIgnoreCaseTest() {
        List<TechnicReadDto> phones = technicService.findByNameContainingIgnoreCase("phone");
        Optional<TechnicReadDto> product = phones.stream().filter(phone -> phone.getId() == 66L).findFirst();

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

    @Test
    @SneakyThrows
    void createTest() {
            TechnicCreateDto technic = TechnicCreateDto.builder()
                    .name("test")
                    .category(PHONE)
                    .description("test")
                    .price(1)
                    .amount(1)
                    .image(new MockMultipartFile("test", new byte[0]))
                    .build();

            TechnicReadDto technicReadDto = technicService.create(technic);

            assertEquals(technic.getName(), technicReadDto.getName());
            assertEquals(technic.getCategory().name(), technicReadDto.getCategory());
            assertEquals(technic.getDescription(), technicReadDto.getDescription());
            assertEquals(technic.getPrice(), technicReadDto.getPrice());
            assertEquals(technic.getAmount(), technicReadDto.getAmount());
    }

    @Test
    void findByUserIdBasketTest() {
        List<TechnicReadDto> technics = technicService.findByUserIdBasket(5L);

        assertEquals(6, technics.size());

        List<String> list = technics.stream()
                .map(TechnicReadDto::getName).toList();

        assertTrue(list.contains("Samsung Galaxy M12 64GB"));
    }

    @Test
    void updateTest() {
        TechnicCreateDto technicCreateDto = TechnicCreateDto.builder()
                .name("test")
                .description("test")
                .price(3)
                .amount(3)
                .build();

        Optional<TechnicReadDto> maybeTechnic = technicService.update(1L, technicCreateDto);

        assertTrue(maybeTechnic.isPresent());

        maybeTechnic.ifPresent(technic-> {
            assertEquals(technicCreateDto.getName(), technic.getName());
            assertEquals(technicCreateDto.getDescription(), technic.getDescription());
            assertEquals(technicCreateDto.getPrice(), technic.getPrice());
            assertEquals(technicCreateDto.getAmount(), technic.getAmount());
        });
    }

    @Test
    void deleteTest(){
        assertTrue(technicService.delete(1L));
        assertFalse(technicService.delete(100L));
    }

}