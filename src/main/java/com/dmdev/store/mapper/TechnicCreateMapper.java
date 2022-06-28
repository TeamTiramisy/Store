package com.dmdev.store.mapper;

import com.dmdev.store.database.entity.Category;
import com.dmdev.store.database.entity.Technic;
import com.dmdev.store.dto.TechnicCreateDto;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;
import java.util.function.Predicate;

@Component
public class TechnicCreateMapper implements Mapper<TechnicCreateDto, Technic> {
    @Override
    public Technic map(TechnicCreateDto object) {
        Technic technic = Technic.builder()
                .name(object.getName())
                .category(object.getCategory())
                .description(object.getDescription())
                .price(object.getPrice())
                .amount(object.getAmount())
                .build();

        Optional.ofNullable(object.getImage())
                .filter(Predicate.not(MultipartFile::isEmpty))
                .ifPresent(image -> technic.setImage(technic.getCategory() + "/" +image.getOriginalFilename()));

        return technic;
    }
}
