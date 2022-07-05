package com.dmdev.store.dto;

import com.dmdev.store.database.entity.BlackList;
import com.dmdev.store.database.entity.Gender;
import com.dmdev.store.database.entity.Role;
import lombok.Builder;
import lombok.Value;
import lombok.experimental.FieldNameConstants;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Value
@Builder
@FieldNameConstants
public class UserCreateDto {

    Long id;


    @NotBlank
    String firstname;

    @NotBlank
    String lastname;

    @Email
    @NotEmpty
    String email;

    @NotBlank
    String rawPassword;

    @NotEmpty
    String tel;

    @NotEmpty
    String address;

    Role role;

    Gender gender;

    BlackList blacklist;
}
