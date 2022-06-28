package com.dmdev.store.dto;

import com.dmdev.store.database.entity.Category;
import lombok.Builder;
import lombok.Value;
import lombok.experimental.FieldNameConstants;
import org.springframework.web.multipart.MultipartFile;

@Value
@Builder
@FieldNameConstants
public class TechnicCreateDto {

    String name;
    Category category;
    String description;
    Integer price;
    Integer amount;
    MultipartFile image;
}
