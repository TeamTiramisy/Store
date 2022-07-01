package com.dmdev.store.dto;

import com.dmdev.store.database.entity.Category;
import lombok.Builder;
import lombok.Value;
import lombok.experimental.FieldNameConstants;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

@Value
@Builder
@FieldNameConstants
public class TechnicCreateDto {

    @NotEmpty
    String name;

    Category category;
    @NotEmpty
    String description;

    @NotNull
    @Positive
    Integer price;

    @NotNull
    @PositiveOrZero
    Integer amount;

    MultipartFile image;
}
