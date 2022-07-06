package com.dmdev.store.http.controller;

import com.dmdev.store.annotation.IT;
import com.dmdev.store.database.entity.Role;
import com.dmdev.store.service.TechnicService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static com.dmdev.store.database.entity.Role.*;
import static com.dmdev.store.dto.TechnicCreateDto.Fields.*;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@IT
@AutoConfigureMockMvc
@RequiredArgsConstructor
@WithMockUser(username = "ruslankarina1.2@gmail.com", password = "test", authorities = {"ADMIN", "USER"})
class BasketControllerTest {

    private final MockMvc mockMvc;

    @Test
    @SneakyThrows
    void findAllCategoryTest(){
        mockMvc.perform(get("/store/basket"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("basket/basket"))
                .andExpect(model().attributeExists("technics"))
                .andExpect(model().attribute("technics", hasSize(6)));
    }

    @Test
    @SneakyThrows
    void createTest(){
        mockMvc.perform(post("/store/basket/6"))
                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrl("/store"));
    }

    @Test
    @SneakyThrows
    void deleteTest(){
        mockMvc.perform(post("/store/basket/6/delete"))
                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrl("/store/basket"));
    }

    @Test
    @SneakyThrows
    void deleteExceptionTest(){
        mockMvc.perform(post("/store/basket/66/delete"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("error/error"));
    }
}