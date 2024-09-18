package com.assinger.taskservice.exception;

import com.assinger.taskservice.dto.ErrorResponseDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> taskNotFoundExceptionHandler(TaskNotFoundException ex, WebRequest webRequest){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ErrorResponseDto(webRequest.getDescription(false),HttpStatus.NOT_FOUND,
                ex.getMessage(),null, LocalDateTime.now())
        );
    }

    @ExceptionHandler(ThreadNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> threadNotFoundExceptionHandler(ThreadNotFoundException ex, WebRequest webRequest){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ErrorResponseDto(webRequest.getDescription(false),HttpStatus.NOT_FOUND,
                        ex.getMessage(),null, LocalDateTime.now())
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> globalExceptionHandler(Exception ex,WebRequest webRequest){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new ErrorResponseDto(webRequest.getDescription(false),HttpStatus.INTERNAL_SERVER_ERROR,
                        ex.getMessage(),null, LocalDateTime.now())
        );
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ErrorResponseDto> unauthorizedExceptionHandler(UnauthorizedException ex,WebRequest webRequest){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                new ErrorResponseDto(webRequest.getDescription(false),HttpStatus.UNAUTHORIZED,
                        ex.getMessage(),null, LocalDateTime.now())
        );
    }


    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        Map<String,String> validationErros = new HashMap<>();
        List<ObjectError> errorList = ex.getBindingResult().getAllErrors();
        StringBuilder errorMessage  = new StringBuilder();
        errorList.forEach( error -> {
            String fieldName = ((FieldError) error).getField();
            validationErros.put(fieldName, error.getDefaultMessage());
            errorMessage.append(error.getDefaultMessage()).append(" ");
        });

        ErrorResponseDto errorResponseDto  = new ErrorResponseDto();
        errorResponseDto.setApiPath(request.getDescription(false));
        errorResponseDto.setErrorCode(HttpStatus.BAD_GATEWAY);
        errorResponseDto.setErrorTime(LocalDateTime.now());
        errorResponseDto.setMessage("Request Validation Error : "+errorMessage);
        errorResponseDto.setErrorList(validationErros);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponseDto);

    }
}
