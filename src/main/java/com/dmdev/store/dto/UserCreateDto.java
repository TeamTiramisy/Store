package com.dmdev.store.dto;

import com.dmdev.store.database.entity.BlackList;
import com.dmdev.store.database.entity.Gender;
import com.dmdev.store.database.entity.Role;
import lombok.Builder;
import lombok.Value;
import lombok.experimental.FieldNameConstants;

@Value
@Builder
@FieldNameConstants
public class UserCreateDto {

    Long id;
    String firstname;
    String lastname;
    String email;
    String password;
    String tel;
    String address;
    Role role;
    Gender gender;
    BlackList blacklist;
}
