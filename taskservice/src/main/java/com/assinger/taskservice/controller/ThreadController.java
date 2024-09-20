package com.assinger.taskservice.controller;


import com.assinger.taskservice.constants.TaskConstants;
import com.assinger.taskservice.dto.ResponseDto;
import com.assinger.taskservice.dto.ThreadDto;
import com.assinger.taskservice.service.IThreadService;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value ="/api/tasks",produces = MediaType.APPLICATION_JSON_VALUE)

@AllArgsConstructor
@Validated
public class ThreadController {

    private IThreadService threadService;

    @Retry(name="getALlThreads",fallbackMethod = "getAllThreadFallBack")//retry and properties added
    @GetMapping("/{taskId}/threads")
    public ResponseEntity<List<ThreadDto>> getAllThreadsByTask(@PathVariable Long taskId){

        List<ThreadDto> threadDtoList = threadService.findThreadByTask(taskId);
        return ResponseEntity.status(HttpStatus.OK).body(threadDtoList);
    }

    private ResponseEntity<List<ThreadDto>> getAllThreadFallBack(Throwable th){

        return  null;
    }


    @RateLimiter(name="saveThreadPostRateLimit",fallbackMethod = "saveThreadFallBack")
    @PostMapping("/{taskId}/threads")
    public ResponseEntity<ResponseDto> saveThread(@PathVariable Long taskId, @Valid @RequestBody ThreadDto threadDto){

        threadService.saveThread(taskId,threadDto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseDto(TaskConstants.STATUS_201,"THREAD ADDED SUCCESSFULLY!"));
    }


    private ResponseEntity<ResponseDto> saveThreadFallBack(Throwable th) {
        return null;
    }


        @DeleteMapping("/threads/{threadId}")
    public ResponseEntity<ResponseDto> deleteSingleThread(@PathVariable Long threadId){

        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseDto(TaskConstants.STATUS_200,"THREAD DELETED SUCCESSFULLY!"));
    }

}
