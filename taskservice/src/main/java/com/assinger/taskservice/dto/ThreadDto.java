package com.assinger.taskservice.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class ThreadDto extends AuditBaseDto {

    private Long threadId;

    private String subject;

    private String remarks;

    private String fileName;


}
