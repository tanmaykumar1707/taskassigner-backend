package com.assinger.taskusers.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserDto extends  AuditBaseDto {

    private Long userId;

    @NotEmpty(message="Name can not be null or empty")
   // @Pattern(regexp="(^$[0-9]{10})",message="Account number must be 10 digit!")
    private String name;

    @NotEmpty(message="Email can not be null or empty")
    @Email(message = "Email address should be a valid value")
    private String email;

    @NotEmpty(message="MobileNumber can not be null or empty")
    private String mobileNumber;

    @NotEmpty(message="Password can not be null or empty")
    private String password;

    private boolean enabled;

    @NotEmpty(message="Role can not be null or empty")
    private String role;


}
