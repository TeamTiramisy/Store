package com.dmdev.store.http.controller;

import com.dmdev.store.StoreTest;
import com.dmdev.store.annotation.IT;
import com.dmdev.store.database.entity.Role;
import com.dmdev.store.dto.OrderCreateDto;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static com.dmdev.store.dto.UserCreateDto.Fields.*;
import static com.dmdev.store.dto.UserCreateDto.Fields.address;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@AutoConfigureMockMvc
@RequiredArgsConstructor
class OrderControllerTest extends StoreTest {

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

    @Test
    @SneakyThrows
    void  findByUserTest() {
        mockMvc.perform(get("/store/order")
                        .with(user("rusya-niyazov@mail.ru").authorities(Role.ADMIN)))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("order/order"))
                .andExpect(model().attributeExists("orders"))
                .andExpect(model().attribute("orders", hasSize(6)));
    }

    @Test
    @SneakyThrows
    void  findByUserIdTest() {
        mockMvc.perform(get("/store/order/user/1"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("order/orderuser"))
                .andExpect(model().attributeExists("orders"))
                .andExpect(model().attribute("orders", hasSize(6)));
    }

    @Test
    @SneakyThrows
    void createTest(){
        mockMvc.perform(post("/store/ordering")
                        .param("amount", "1", "1", "1", "1", "1", "1"))
                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrl("/store/order")
                );
    }

    @Test
    @SneakyThrows
    void deleteTest(){
        mockMvc.perform(post("/store/order/1/delete")
                        .with(user("rusya-niyazov@mail.ru").authorities(Role.ADMIN)))
                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrl("/store/order")
                );
    }

    @Test
    @SneakyThrows
    void deleteExceptionTest(){
        mockMvc.perform(post("/store/order/100/delete"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("error/error"));
    }

    @Test
    @SneakyThrows
    void  findAllByProductTest() {
        mockMvc.perform(get("/store/order/Sony PlayStation 5"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("order/product"))
                .andExpect(model().attributeExists("name"))
                .andExpect(model().attributeExists("orders"))
                .andExpect(model().attribute("orders", hasSize(2)));
    }

    @Test
    @SneakyThrows
    void  findTest() {
        mockMvc.perform(get("/store/admin/orders"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("order/orders"))
                .andExpect(model().attributeExists("orders"))
                .andExpect(model().attribute("orders", hasSize(9)));
    }

    @Test
    @SneakyThrows
    void  deleteDateCloseTest() {
        mockMvc.perform(post("/store/admin/orders/delete")
                        .param("date", "2022-07-06"))
                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrl("/store/admin/orders"));
    }

    @Test
    @SneakyThrows
    void  findAllProcessingTest() {
        mockMvc.perform(get("/store/admin/orders/processing"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("order/processing"))
                .andExpect(model().attributeExists("orders"))
                .andExpect(model().attribute("orders", hasSize(9)));
    }

    @Test
    @SneakyThrows
    void  updateAcceptTest() {
        mockMvc.perform(post("/store/admin/orders/processing/1/accept"))
                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrl("/store/admin/orders/processing"));
    }

    @Test
    @SneakyThrows
    void  updateAcceptExceptionTest() {
        mockMvc.perform(post("/store/admin/orders/processing/100/accept"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("error/error"));
    }

    @Test
    @SneakyThrows
    void  updateRejectTest() {
        mockMvc.perform(post("/store/admin/orders/processing/1/reject"))
                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrl("/store/admin/orders/processing"));
    }

    @Test
    @SneakyThrows
    void  updateRejectExceptionTest() {
        mockMvc.perform(post("/store/admin/orders/processing/100/reject"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("error/error"));
    }

    @Test
    @SneakyThrows
    void  findAllCompletedTest() {
        mockMvc.perform(get("/store/admin/orders/completed"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("order/completed"))
                .andExpect(model().attributeExists("orders"))
                .andExpect(model().attribute("orders", hasSize(9)));
    }

    @Test
    @SneakyThrows
    void  updateCompletedTest() {
        mockMvc.perform(post("/store/admin/orders/processing/1/completed"))
                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrl("/store/admin/orders/completed"));
    }

    @Test
    @SneakyThrows
    void  updateCompletedExceptionTest() {
        mockMvc.perform(post("/store/admin/orders/processing/100/completed"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("error/error"));

    }
}