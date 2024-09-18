package com.assinger.taskservice.dto;


import lombok.AllArgsConstructor;
import lombok.Data;



@Data @AllArgsConstructor
public class UserDto {

    private Long userId;
    private String name;
    private String email;
    private String mobileNumber;

}
