package com.assinger.taskusers.exception;

import com.assinger.taskusers.audit.AuditAwareImpl;
import com.assinger.taskusers.dto.ErrorResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {


//    @Autowired
//    private AuditAwareImpl auditAwareImpl;

//    @ModelAttribute
//    public void setAuditor(@RequestHeader("X-Current-Auditor") String auditor) {
//        auditAwareImpl.setCurrentAuditor(auditor);
//    }

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

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> exceptionHandler(Exception ex, WebRequest webRequest){

        ErrorResponseDto errorResponseDto  = new ErrorResponseDto();
        errorResponseDto.setApiPath(webRequest.getDescription(false));
        errorResponseDto.setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR);
        errorResponseDto.setErrorTime(LocalDateTime.now());
        errorResponseDto.setMessage("Error Occured "+ex.getMessage());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponseDto);

    }

    @ExceptionHandler(UserNotFoundException.class)
    public  ResponseEntity<ErrorResponseDto> userNotFoundExceptionHandler(UserNotFoundException ex,WebRequest webRequest){

        ErrorResponseDto errorResponseDto  = new ErrorResponseDto();
        errorResponseDto.setApiPath(webRequest.getDescription(false));
        errorResponseDto.setErrorCode(HttpStatus.NOT_FOUND);
        errorResponseDto.setErrorTime(LocalDateTime.now());
        errorResponseDto.setMessage(ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponseDto);

    }

}
