package com.dmdev.store.http.controller;

import com.dmdev.store.service.BasketService;
import com.dmdev.store.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;

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

    @GetMapping("/order")
    public String findByUser(Model model,
                                @AuthenticationPrincipal UserDetails userDetails){
        model.addAttribute("orders", orderService.findAllByUser(userDetails.getUsername()));
        return "order/order";
    }

    @GetMapping("/order/user/{id}")
    public String findByUserId(@PathVariable("id") Long id, Model model){
        model.addAttribute("orders", orderService.findAllByUser(id));
        return "order/orderuser";
    }

    @PostMapping("/ordering")
    public String create(@RequestParam("amount") Integer[] amounts,
                         @AuthenticationPrincipal UserDetails userDetails){
        orderService.create(userDetails.getUsername(), amounts);
        return "redirect:/store/order";
    }

    @PostMapping("/order/{id}/delete")
    public String delete(@PathVariable Long id){
        if (!orderService.delete(id)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return "redirect:/store/order";
    }

    @GetMapping("/order/{name}")
    public String findAllByProduct(Model model, @PathVariable String name){
        model.addAttribute("name", name);
        model.addAttribute("orders", orderService.findAllByProduct(name));
        return "order/product";
    }

    @GetMapping("/admin/orders")
    public String findAll(Model model){
        model.addAttribute("orders", orderService.findAll());
        return "order/orders";
    }

    @PostMapping("/admin/orders/delete")
    public String deleteDateClose(@RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date){
        orderService.deleteDateClose(date);
        return "redirect:/store/admin/orders";
    }

    @GetMapping("/admin/orders/processing")
    public String findAllProcessing(Model model){
        model.addAttribute("orders", orderService.findAll());
        return "order/processing";
    }

    @PostMapping("/admin/orders/processing/{id}/accept")
    public String updateAccept(@PathVariable Long id){
        return orderService.updateAccept(id)
                .map(it -> "redirect:/store/admin/orders/processing")
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

    }

    @PostMapping("/admin/orders/processing/{id}/reject")
    public String updateReject(@PathVariable Long id){
        return orderService.updateReject(id)
                .map(it -> "redirect:/store/admin/orders/processing")
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

    }

    @GetMapping("/admin/orders/completed")
    public String findAllCompleted(Model model){
        model.addAttribute("orders", orderService.findAll());
        return "order/completed";
    }

    @PostMapping("/admin/orders/processing/{id}/completed")
    public String updatCompleted(@PathVariable Long id){
        return orderService.updateCompleted(id)
                .map(it -> "redirect:/store/admin/orders/completed")
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

    }
}
