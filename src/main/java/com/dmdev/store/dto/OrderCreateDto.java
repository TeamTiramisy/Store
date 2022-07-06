package com.dmdev.store.dto;

import com.dmdev.store.database.entity.Status;
import com.dmdev.store.database.entity.User;
import lombok.Builder;
import lombok.Value;
import lombok.experimental.FieldNameConstants;

import javax.persistence.*;
import java.time.LocalDate;

@Value
@Builder
public class OrderCreateDto {

    String product;

    Long userId;

    LocalDate dateRegistration;

    LocalDate dateClose;

    Status status;

    Integer total;
}
