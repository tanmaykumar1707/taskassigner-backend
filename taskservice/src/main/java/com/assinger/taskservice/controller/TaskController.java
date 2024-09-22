package com.assinger.taskservice.controller;

import com.assinger.taskservice.aspects.annotation.OnlyAdmin;
import com.assinger.taskservice.constants.TaskConstants;
import com.assinger.taskservice.dto.ErrorResponseDto;
import com.assinger.taskservice.dto.ResponseDto;
import com.assinger.taskservice.dto.TaskCountDto;
import com.assinger.taskservice.dto.TaskDto;
import com.assinger.taskservice.enums.TaskStatusEnum;
import com.assinger.taskservice.exception.UnauthorizedException;
import com.assinger.taskservice.service.ITaskService;
import com.assinger.taskservice.utils.JwtUtils;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.config.Task;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.SignatureException;
import java.util.List;

@RestController
@RequestMapping(value="/api/tasks",produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
@Slf4j
@Validated
public class TaskController {

    private ITaskService taskService;
    private JwtUtils jwtUtils;

    @GetMapping
    public ResponseEntity<List<TaskDto>> getAllTasks(){
            List<TaskDto> taskDtoList=  taskService.fetchAllTasks();

        return ResponseEntity.status(HttpStatus.OK).body(taskDtoList);
    }


    @PostMapping
    public ResponseEntity<ResponseDto> saveTask(@Valid @RequestBody TaskDto taskDto,@RequestHeader("Authorization") String jwtToken){
        log.info("jwt token ::"+jwtToken);

        taskService.saveTask(taskDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ResponseDto(TaskConstants.STATUS_201,TaskConstants.TASK_CREATION_MESSAGE));
    }


    @PutMapping("/{taskId}")
    public ResponseEntity<ResponseDto> updateTask(@PathVariable Long taskId,@Valid @RequestBody TaskDto taskDto){

        taskService.updateTasks(taskId,taskDto);

        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseDto(TaskConstants.STATUS_200,TaskConstants.TASK_UPDATED_MESSAGE));

    }




    @OnlyAdmin
    @DeleteMapping("/{taskId}")
    public ResponseEntity<ResponseDto> deleteTask(@PathVariable Long taskId ){
        taskService.deleteTask(taskId);
        return  ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseDto(TaskConstants.STATUS_200,TaskConstants.TASK_DELETE_MESSAGE));
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<TaskDto> fetchSingleTask(@PathVariable Long taskId ){
        TaskDto taskDto = taskService.fetchSingleTask(taskId);
        return  ResponseEntity.status(HttpStatus.OK)
                .body(taskDto);
    }


    @GetMapping("/counts")
    public ResponseEntity<TaskCountDto> getAllTaskCount(){
        TaskCountDto taskCountDto  = taskService.taskCountByStatus();
        return  ResponseEntity.ok().body(taskCountDto);
    }

    @PatchMapping("/task-status/{taskId}/{status}")
    public  ResponseEntity<ResponseDto> updateTaskStatus(@PathVariable Long taskId, @PathVariable TaskStatusEnum status){

            taskService.updateTaskStatus(taskId,status);

        return ResponseEntity.ok()
                .body(new ResponseDto(TaskConstants.STATUS_200,TaskConstants.TASK_STATUS_UPDATE_MESSAGE));
    }

}
