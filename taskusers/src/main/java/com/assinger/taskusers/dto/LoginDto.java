package com.assinger.taskusers.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class LoginDto {

    @NotEmpty(message = "Email id is mandatory")
    @Email(message = "Enter valid Email id!")
    private String email;

    @NotEmpty
    private String password;
}
