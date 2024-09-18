package com.assinger.taskservice.mapper;

import com.assinger.taskservice.dto.TaskDto;
import com.assinger.taskservice.entity.TaskEntity;
import org.springframework.scheduling.config.Task;

public class TaskMapper {

    private TaskMapper(){};

    public static  TaskEntity mapToTaskEntity(TaskEntity taskEntity, TaskDto taskDto){

        taskEntity.setSubject(taskDto.getSubject());
        taskEntity.setStatus(taskDto.getStatus());
        taskEntity.setRemarks(taskDto.getRemarks());
        taskEntity.setAssignedTo(taskDto.getAssignedTo());
        taskEntity.setAssignedToType(taskDto.getAssignedToType());


        return taskEntity;
    }

    public static  TaskDto mapToTaskDto( TaskDto taskDto, TaskEntity taskEntity){

        taskDto.setTaskId(taskEntity.getTaskId());
        taskDto.setSubject(taskEntity.getSubject());
        taskDto.setStatus(taskEntity.getStatus());
        taskDto.setRemarks(taskEntity.getRemarks());
        taskDto.setAssignedTo(taskEntity.getAssignedTo());
        taskDto.setAssignedToType(taskEntity.getAssignedToType());
        taskDto.setCreatedAt(taskEntity.getCreatedAt());
        taskDto.setUpdatedAt(taskEntity.getUpdatedAt());

        return taskDto;
    }
}
