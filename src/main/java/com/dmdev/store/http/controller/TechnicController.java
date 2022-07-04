package com.dmdev.store.http.controller;

import com.dmdev.store.database.entity.Category;
import com.dmdev.store.dto.TechnicCreateDto;
import com.dmdev.store.dto.TechnicReadDto;
import com.dmdev.store.service.TechnicService;
import com.dmdev.store.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Controller
@RequestMapping("/store")
@RequiredArgsConstructor
public class TechnicController {

    private final TechnicService technicService;
    private final UserService userService;

    @GetMapping
    public String findAllCategory(Model model){
        model.addAttribute("categories", technicService.findAllCategory());
        return "technic/category";
    }

    @GetMapping("/{value}")
    public String findAllByCategory(@PathVariable Category value, Model model,
                                    @AuthenticationPrincipal UserDetails userDetails){
        model.addAttribute("user", userService.findByEmail(userDetails.getUsername()).orElseThrow());
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

    @GetMapping("/{value}/{id}/update")
    public String pageUpdate(@PathVariable Long id, Model model){
        technicService.findById(id)
                .map(technicReadDto -> {
                    model.addAttribute("technic", technicReadDto);
                    return "technic/update";
                })
                .orElseThrow((() -> new ResponseStatusException(NOT_FOUND)));
        return "technic/update";
    }

    @PostMapping("/{value}/{id}/update")
    public String update(@PathVariable Long id, Model model, @Validated TechnicCreateDto technic,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes){
        if (bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("technic", technic);
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            return "redirect:/store/{value}/{id}/update";
        }
        return technicService.update(id, technic)
                .map(it -> "redirect:/store/{value}/{id}/update")
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

    }

    @GetMapping("/search")
    public String findAllByNameContaining(@RequestParam String search, Model model){
        model.addAttribute("technics", technicService.findByNameContainingIgnoreCase(search));
        return "technic/search";
    }

    @GetMapping("/admin/add")
    public String addProduct(Model model, @ModelAttribute("technic") TechnicCreateDto technic){
        model.addAttribute("technic", technic);
        model.addAttribute("category", Category.values());
        return "technic/add";
    }

    @PostMapping("/admin/add/create")
    public String create(@ModelAttribute @Validated TechnicCreateDto technic,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes){
        if (bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("technic", technic);
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            return "redirect:/store/admin/add";
        }
        TechnicReadDto technicReadDto = technicService.create(technic);
        return "redirect:/store/" + technicReadDto.getCategory() + "/" + technicReadDto.getId();
    }
}
