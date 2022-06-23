package com.dmdev.store.http.controller;

import com.dmdev.store.annotation.IT;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.hamcrest.collection.IsCollectionWithSize;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.server.ResponseStatusException;

import static org.hamcrest.collection.IsCollectionWithSize.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@IT
@AutoConfigureMockMvc
@RequiredArgsConstructor
class TechnicControllerTest {

    private final MockMvc mockMvc;

    @Test
    @SneakyThrows
    void findAllCategoryTest(){
        mockMvc.perform(get("/store"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("technic/category"))
                .andExpect(model().attributeExists("categories"))
                .andExpect(model().attribute("categories", hasSize(6)));
    }

    @Test
    @SneakyThrows
    void findAllByCategoryTest(){
        mockMvc.perform(get("/store/PHONE"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("technic/technic"))
                .andExpect(model().attributeExists("technics"))
                .andExpect(model().attribute("technics", hasSize(17)));
    }

    @Test
    @SneakyThrows
    void findByIdTest(){
        mockMvc.perform(get("/store/PHONE/1"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("technic/product"))
                .andExpect(model().attributeExists("product"));
    }

    @Test
    @SneakyThrows
    void findByIdNotFoundTest(){
        mockMvc.perform(get("/store/PHONE/100"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @SneakyThrows
    void findAllByNameContainingTest(){
        mockMvc.perform(get("/store/search?search=phone"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("technic/search"))
                .andExpect(model().attributeExists("technics"))
                .andExpect(model().attribute("technics", hasSize(5)));
    }

}