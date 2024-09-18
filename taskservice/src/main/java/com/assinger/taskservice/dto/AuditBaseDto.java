package com.assinger.taskservice.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class AuditBaseDto {

    private LocalDateTime createdAt;

    private UserDto createdBy;

    private LocalDateTime updatedAt;

    private UserDto updatedBy;
}
