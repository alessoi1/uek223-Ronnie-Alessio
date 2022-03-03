package com.example.demo.domain.appUser;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.UniqueConstraint;

@Getter @Setter
public class UserUpdateDTO {

    private String username;

    private String email;

    private String password;
}
