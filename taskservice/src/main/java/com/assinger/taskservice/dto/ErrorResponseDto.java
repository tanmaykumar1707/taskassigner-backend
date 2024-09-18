package com.assinger.taskservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Map;


@Data @AllArgsConstructor @NoArgsConstructor
public class ErrorResponseDto {

    @Schema(description = "Api Path invoked by the client")
    private String apiPath;


    @Schema(
            description = "Error code representing the error happened"
    )
    private HttpStatus errorCode;

    @Schema(
            description = "Error message representing the error happened"
    )
    private String message;

    private Map errorList;

    @Schema(
            description = "Time representing when the error happened"
    )

    private LocalDateTime errorTime;
}
