package com.dmdev.store.http.controller;

import com.dmdev.store.annotation.IT;
import com.dmdev.store.database.entity.Role;
import com.dmdev.store.dto.TechnicCreateDto;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.hamcrest.collection.IsCollectionWithSize;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.HttpStatus;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.server.ResponseStatusException;

import static com.dmdev.store.dto.TechnicCreateDto.*;
import static com.dmdev.store.dto.TechnicCreateDto.Fields.*;
import static org.hamcrest.collection.IsCollectionWithSize.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@IT
@AutoConfigureMockMvc
@RequiredArgsConstructor
@WithMockUser(username = "test@gmail.com", password = "test", authorities = {"ADMIN", "USER"})
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
        mockMvc.perform(get("/store/PHONE")
                        .with(user("ruslankarina1.2@gmail.com").authorities(Role.ADMIN)))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("technic/technic"))
                .andExpect(model().attributeExists("technics"))
                .andExpect(model().attribute("technics", hasSize(17)));
    }

    @Test
    @SneakyThrows
    void findByIdTest(){
        mockMvc.perform(get("/store/PHONE/1")
                        .with(user("ruslankarina1.2@gmail.com").authorities(Role.ADMIN)))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("technic/product"))
                .andExpect(model().attributeExists("product"));
    }

    @Test
    @SneakyThrows
    void findByIdNotFoundTest(){
        mockMvc.perform(get("/store/PHONE/100")
                        .with(user("ruslankarina1.2@gmail.com").authorities(Role.ADMIN)))
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

    @Test
    @SneakyThrows
    void addProductTest(){
        mockMvc.perform(get("/store/admin/add"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("technic/add"))
                .andExpect(model().attributeExists("technic"))
                .andExpect(model().attributeExists("category"));
    }

    @Test
    @SneakyThrows
    void createTest(){
        mockMvc.perform(post("/store/admin/add/create")
                .param(name, "test")
                .param(category, "PHONE")
                .param(description, "test")
                .param(price, "1")
                .param(amount, "1"))
                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrlPattern("/store/PHONE/{\\d+}"));
    }

    @Test
    @SneakyThrows
    void createValidTest(){
        mockMvc.perform(post("/store/admin/add/create")
                        .with(user("test@gmail.com").authorities(Role.ADMIN)))
                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrl("/store/admin/add")
                );
    }

    @Test
    @SneakyThrows
    void pageUpdateExceptionTest(){
        mockMvc.perform(get("/store/PHONE/100/update"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @SneakyThrows
    void pageUpdateTest(){
        mockMvc.perform(get("/store/PHONE/1/update"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("technic/update"))
                .andExpect(model().attributeExists("technic"));
    }

    @Test
    @SneakyThrows
    void updateTest(){
        mockMvc.perform(post("/store/PHONE/1/update")
                .param(name, "test")
                .param(description, "test")
                .param(price, "6")
                .param(amount, "6")
        )
                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrl("/store/PHONE/1/update")
                );
    }


    @Test
    @SneakyThrows
    void deleteTest(){
        mockMvc.perform(post("/store/PHONE/1/delete"))
                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrl("/store/PHONE")
                );

    }

    @Test
    @SneakyThrows
    void deleteExceptionTest(){
        mockMvc.perform(post("/store/PHONE/100/delete"))
                .andExpect(
                        status().is4xxClientError());

    }
}