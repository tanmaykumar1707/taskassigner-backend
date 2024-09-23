package com.assinger.taskgateway.Dto;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data @AllArgsConstructor
public class FallbackDto {

    public String statusCode;
    public String message;

}
