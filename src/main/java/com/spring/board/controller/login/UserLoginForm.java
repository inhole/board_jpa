package com.spring.board.controller.login;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserLoginForm {

    @NotBlank
    private String loginId;

    @NotBlank
    private String password;
}
