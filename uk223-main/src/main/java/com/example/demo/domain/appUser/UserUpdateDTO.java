package com.example.demo.domain.appUser;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserUpdateDTO {

    private String username;

    private String email;

    private String password;
}
