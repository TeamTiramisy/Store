package com.dmdev.store.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class UserReadDto {

    Long id;
    String firstname;
    String lastname;
    String email;
    String password;
    String tel;
    String address;
    String role;
    String gender;
    String blacklist;
}
