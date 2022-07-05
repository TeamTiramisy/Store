package com.dmdev.store.http.controller;

import com.dmdev.store.service.BasketService;
import com.dmdev.store.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/store")
@RequiredArgsConstructor
public class OrderController {

    private final BasketService basketService;
    private final OrderService orderService;

    @GetMapping("/ordering")
    public String findAllByUser(Model model,
                                @AuthenticationPrincipal UserDetails userDetails) {
        model.addAttribute("technics", basketService.findByUserIdBasket(userDetails.getUsername()));
        return "order/ordering";
    }

//    @PostMapping("/ordering")
//    public String create(@RequestParam("amount") Integer[] amounts){
//        return "order/ordering";
//    }
}
