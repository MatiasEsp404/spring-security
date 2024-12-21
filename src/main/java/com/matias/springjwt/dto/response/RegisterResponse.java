package com.matias.springjwt.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterResponse {

    private Integer id;
    private String username;
    private String email;

}