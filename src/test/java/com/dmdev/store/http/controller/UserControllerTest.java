package com.dmdev.store.http.controller;

import com.dmdev.store.annotation.IT;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static com.dmdev.store.dto.UserCreateDto.Fields.*;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@IT
@AutoConfigureMockMvc
@RequiredArgsConstructor
@WithMockUser(username = "test@gmail.com", password = "test", authorities = {"ADMIN", "USER"})
class UserControllerTest {

    private final MockMvc mockMvc;

    @Test
    @SneakyThrows
    void pageAdminTest() {
        mockMvc.perform(get("/store/admin"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("user/admin"));
    }

    @Test
    @SneakyThrows
    void findAllTest() {
        mockMvc.perform(get("/store/admin/users"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("user/users"))
                .andExpect(model().attributeExists("users"))
                .andExpect(model().attribute("users", hasSize(5)));
    }

    @Test
    @SneakyThrows
    void findByIdTest() {
        mockMvc.perform(get("/store/admin/users/1"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("user/user"))
                .andExpect(model().attributeExists("user"));
    }

    @Test
    @SneakyThrows
    void findByINotFoundTest() {
        mockMvc.perform(get("/store/admin/users/100"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @SneakyThrows
    void updateRoleTest() {
        mockMvc.perform(post("/store/admin/users/1/update"))
                .andExpectAll(status().is3xxRedirection(),
                        redirectedUrl("/store/admin/users/1"));
    }

    @Test
    @SneakyThrows
    void updateBlacklistTest() {
        mockMvc.perform(post("/store/admin/users/1/blacklist"))
                .andExpectAll(status().is3xxRedirection(),
                        redirectedUrl("/store/admin/users/1"));
    }


    @Test
    @SneakyThrows
    void registrationTest(){
        mockMvc.perform(get("/store/registration"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeExists("user"))
                .andExpect(model().attributeExists("genders"))
                .andExpect(view().name("user/registration"));
    }

    @Test
    @SneakyThrows
    void createTest(){
        mockMvc.perform(post("/store/create")
                .param(firstname, "test")
                .param(lastname, "test")
                .param(email, "test@mail.ru")
                .param(rawPassword, "123")
                .param(tel, "+375298888888")
                .param(address, "test")
                .param(gender, "MALE")
        )
                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrl("/login")
                );
    }

    @Test
    @SneakyThrows
    void createValidTest(){
        mockMvc.perform(post("/store/create"))
                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrl("/store/registration")
                );
    }
}
