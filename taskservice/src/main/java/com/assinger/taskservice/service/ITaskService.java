package com.assinger.taskservice.service;

import com.assinger.taskservice.dto.TaskCountDto;
import com.assinger.taskservice.dto.TaskDto;
import com.assinger.taskservice.enums.TaskStatusEnum;
import org.springframework.scheduling.config.Task;

import java.util.List;

public interface ITaskService {

    void saveTask(TaskDto taskDto);

    List<TaskDto> fetchAllTasks();

    void updateTasks(Long taskId, TaskDto taskDto );

    String deleteTask(Long taskId);

    TaskDto fetchSingleTask(Long id);

    TaskCountDto taskCountByStatus();

    void  updateTaskStatus(Long taskId, TaskStatusEnum status);
}
