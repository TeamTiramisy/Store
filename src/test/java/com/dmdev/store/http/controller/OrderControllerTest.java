package com.dmdev.store.http.controller;

import com.dmdev.store.annotation.IT;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@IT
@AutoConfigureMockMvc
@RequiredArgsConstructor
@WithMockUser(username = "ruslankarina1.2@gmail.com", password = "test", authorities = {"ADMIN", "USER"})
class OrderControllerTest {

    private final MockMvc mockMvc;

    @Test
    @SneakyThrows
    void  findAllByUserTest() {
        mockMvc.perform(get("/store/ordering"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("order/ordering"))
                .andExpect(model().attributeExists("technics"))
                .andExpect(model().attribute("technics", hasSize(6)));
    }

}