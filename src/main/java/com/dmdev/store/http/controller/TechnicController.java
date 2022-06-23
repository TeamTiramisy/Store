package com.dmdev.store.http.controller;

import com.dmdev.store.database.entity.Category;
import com.dmdev.store.service.TechnicService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

@Controller
@RequestMapping("/store")
@RequiredArgsConstructor
public class TechnicController {

    private final TechnicService technicService;

    @GetMapping
    public String findAllCategory(Model model){
        model.addAttribute("categories", technicService.findAllCategory());
        return "technic/category";
    }

    @GetMapping("/{value}")
    public String findAllByCategory(@PathVariable Category value, Model model){
        model.addAttribute("category", value.name());
        model.addAttribute("technics", technicService.findAllByCategory(value));
        return "technic/technic";
    }

    @GetMapping("/{value}/{id}")
    public String findById(@PathVariable Long id, Model model){
        return technicService.findById(id)
                .map(technicReadDto -> {
                        model.addAttribute("product" , technicReadDto);
                return "technic/product";
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/search")
    public String findAllByNameContaining(@RequestParam String search, Model model){
        model.addAttribute("technics", technicService.findByNameContainingIgnoreCase(search));
        return "technic/search";
    }
}
