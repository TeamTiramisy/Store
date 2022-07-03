package com.dmdev.store.http.rest;

import com.dmdev.store.service.TechnicService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;


import static org.springframework.http.ResponseEntity.notFound;

import java.io.InputStream;

@RestController
@RequestMapping("/api/v1/store")
@RequiredArgsConstructor
public class TechnicRestController {

    private final TechnicService technicService;

    @GetMapping("/{id}/avatar")
    public byte[] findAvatar(@PathVariable("id") Long id) {
        return technicService.findAvatar(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}
