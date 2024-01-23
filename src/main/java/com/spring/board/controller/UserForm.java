package com.spring.board.controller;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserForm {

    /*
    * @NotNull: null을 허용하지 않음
    * @NotEmpty: null, "" 둘 다 허용하지 않음
    * @NotBlank: null, "", " " 모두 허용하지 않음
    * */

    @NotBlank
    private String loginId;

    @NotBlank
    private String password;

    @NotBlank
    private String name;

    private Integer age;
}
