package com.dmdev.store.dto;

import com.dmdev.store.database.entity.Status;
import com.dmdev.store.database.entity.User;
import lombok.Builder;
import lombok.Value;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDate;

@Value
@Builder
public class OrderReadDto {

    Long id;

    String product;

    UserReadDto user;

    String dateRegistration;

    String dateClose;

    String status;

    Integer total;
}
