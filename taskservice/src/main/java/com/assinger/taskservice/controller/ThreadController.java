package com.assinger.taskservice.controller;


import com.assinger.taskservice.constants.TaskConstants;
import com.assinger.taskservice.dto.ResponseDto;
import com.assinger.taskservice.dto.ThreadDto;
import com.assinger.taskservice.service.IThreadService;
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

    @GetMapping("/{taskId}/threads")
    public ResponseEntity<List<ThreadDto>> getAllThreadsByTask(@PathVariable Long taskId){

        List<ThreadDto> threadDtoList = threadService.findThreadByTask(taskId);
        return ResponseEntity.status(HttpStatus.OK).body(threadDtoList);
    }

    @PostMapping("/{taskId}/threads")
    public ResponseEntity<ResponseDto> saveThread(@PathVariable Long taskId, @Valid @RequestBody ThreadDto threadDto){

        threadService.saveThread(taskId,threadDto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseDto(TaskConstants.STATUS_201,"THREAD ADDED SUCCESSFULLY!"));
    }

    @DeleteMapping("/threads/{threadId}")
    public ResponseEntity<ResponseDto> deleteSingleThread(@PathVariable Long threadId){

        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseDto(TaskConstants.STATUS_200,"THREAD DELETED SUCCESSFULLY!"));
    }

}
