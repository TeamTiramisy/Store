package com.dmdev.store.http.controller;

import com.dmdev.store.database.entity.Gender;
import com.dmdev.store.dto.UserCreateDto;
import com.dmdev.store.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Controller
@RequestMapping("/store")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/admin")
    public String pageAdmin() {
        return "user/admin";
    }

    @GetMapping("/admin/users")
    public String findAll(Model model) {
        model.addAttribute("users", userService.findAll());
        return "user/users";
    }

    @GetMapping("/admin/users/{id}")
    public String findById(@PathVariable Long id, Model model) {
        userService.findById(id)
                .map(user -> {
                    model.addAttribute("user", user);
                    return "user/user";
                })
                .orElseThrow((() -> new ResponseStatusException(NOT_FOUND)));
        return "user/user";
    }

    @PostMapping("/admin/users/{id}/update")
    public String updateRole(@PathVariable Long id){
        return userService.updateRole(id)
                .map(it -> "redirect:/store/admin/users/{id}")
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/admin/users/{id}/blacklist")
    public String updateBlacklist(@PathVariable Long id){
        return userService.updateBlacklist(id)
                .map(it -> "redirect:/store/admin/users/{id}")
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/registration")
    public String registration(Model model, @ModelAttribute UserCreateDto user) {
        model.addAttribute("user", user);
        model.addAttribute("genders", Gender.values());
        return "user/registration";
    }

    @PostMapping
    public String create(@ModelAttribute UserCreateDto user){
        userService.create(user);
        return "redirect:/login";
    }


}
