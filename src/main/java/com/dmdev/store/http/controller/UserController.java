package com.dmdev.store.http.controller;

import com.dmdev.store.database.entity.Gender;
import com.dmdev.store.database.entity.Status;
import com.dmdev.store.dto.UserCreateDto;
import com.dmdev.store.service.OrderService;
import com.dmdev.store.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Collection;
import java.util.List;

import static com.dmdev.store.database.entity.Status.ACCEPTED;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Controller
@RequestMapping("/store")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final OrderService orderService;

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
    public String registration(Model model, @ModelAttribute("user") UserCreateDto user) {
        model.addAttribute("user", user);
        model.addAttribute("genders", Gender.values());
        return "user/registration";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute @Validated UserCreateDto user,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes){
        if (bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("user", user);
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            return "redirect:/store/registration";
        }
            userService.create(user);
            return "redirect:/login";
    }

    @GetMapping("/account")
    public String findId(@AuthenticationPrincipal UserDetails userDetails, Model model){
        return userService.findByEmail(userDetails.getUsername())
                .map(user -> {
                    model.addAttribute("user", user);
                    model.addAttribute("orders",
                            orderService.findAllByStatusByUserId(userDetails.getUsername()));
                    return "user/account";
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/account/update")
    public String update(@AuthenticationPrincipal UserDetails userDetails,
                         @Validated UserCreateDto userCreateDto,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes){
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("user", userCreateDto);
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            return "redirect:/store/account";
        }

        String username = userDetails.getUsername();
        return userService.update(username,userCreateDto)
                .map(it -> "redirect:/store/account")
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/account/delete")
    public String delete(@AuthenticationPrincipal UserDetails userDetails){
        if (!userService.delete(userDetails.getUsername())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return "redirect:/logout";
    }

}
