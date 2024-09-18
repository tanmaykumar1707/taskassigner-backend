package com.assinger.taskservice.dto;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data @AllArgsConstructor @NoArgsConstructor
public class TaskCountDto {

        private int open;
        private int close;
        private int pending;
        private int progress;

}
