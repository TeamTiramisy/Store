package com.dmdev.store.http.controller;

import com.dmdev.store.dto.BasketCreateDto;
import com.dmdev.store.dto.BasketReadDto;
import com.dmdev.store.dto.UserReadDto;
import com.dmdev.store.service.BasketService;
import com.dmdev.store.service.TechnicService;
import com.dmdev.store.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;


@Controller
@RequestMapping("/store/basket")
@RequiredArgsConstructor
public class BasketController {

    private final BasketService basketService;

    @GetMapping
    public String findByUserIdBasket(Model model,
                                     @AuthenticationPrincipal UserDetails userDetails) {
        model.addAttribute("technics", basketService.findByUserIdBasket(userDetails.getUsername()));
        return "basket/basket";

    }

    @PostMapping("/{id}")
    public String create(@PathVariable Long id,
                         @AuthenticationPrincipal UserDetails userDetails,
                         HttpServletRequest req) {
        String email = userDetails.getUsername();
        basketService.create(id, email);

        var prevPage = req.getHeader("referer");
        var page = prevPage != null ? prevPage : "/store";
        return "redirect:" + page;
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id,
                         @AuthenticationPrincipal UserDetails userDetails) {
        if (!basketService.delete(userDetails.getUsername(), id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return "redirect:/store/basket";
    }
}
