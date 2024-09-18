package com.assinger.taskservice.service.impl;

import com.assinger.taskservice.dto.TaskCountDto;
import com.assinger.taskservice.dto.TaskDto;
import com.assinger.taskservice.dto.UserDto;
import com.assinger.taskservice.entity.TaskEntity;
import com.assinger.taskservice.enums.TaskStatusEnum;
import com.assinger.taskservice.mapper.TaskMapper;
import com.assinger.taskservice.repository.TaskRepository;
import com.assinger.taskservice.service.ITaskService;
import com.assinger.taskservice.service.client.UserFeignClient;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class TaskServiceImpl implements ITaskService {

    private TaskRepository taskRepository;
    private UserFeignClient userFeignClient;

    @Override
    public void saveTask(TaskDto taskDto) {

        TaskEntity taskEntity = new TaskEntity();
        TaskMapper.mapToTaskEntity(taskEntity,taskDto);
        taskEntity.setStatus(TaskStatusEnum.OPEN);

        taskRepository.save(taskEntity);

    }

    @Override
    public List<TaskDto> fetchAllTasks() {

        List<TaskEntity> taskEntityList= taskRepository.findAll();
        List<TaskDto> taskDtoList = new ArrayList<>();
        taskEntityList.forEach(taskEntity -> {
            TaskDto taskDto = new TaskDto();
            TaskMapper.mapToTaskDto(taskDto,taskEntity);
            try{
                if(taskEntity.getCreatedBy()!=null){
                    taskDto.setCreatedBy( userFeignClient.fetchUserById(taskEntity.getAssignedTo(),"internal").getBody()
                    );
                }
                if(taskEntity.getUpdatedBy()!=null){
                    taskDto.setUpdatedBy( userFeignClient.fetchUserById(taskEntity.getUpdatedBy(),"internal").getBody()
                    );
                }

                Optional.ofNullable(taskEntity.getAssignedFrom()).ifPresent( userId->
                        taskDto.setAssignedFrom( userFeignClient.fetchUserById(userId,"internal").getBody())
                        );
                Optional.ofNullable(taskEntity.getAssignedTo()).ifPresent( userId->
                        taskDto.setAssignedToObj( userFeignClient.fetchUserById(userId,"internal").getBody())
                );

            }catch (Exception e){

            }
            taskDtoList.add(taskDto);
        });

        return taskDtoList;
    }

    @Override
    public void updateTasks(Long taskId, TaskDto taskDto) {


    }

    @Override
    public String deleteTask(Long taskId) {

        taskRepository.findById(taskId).orElseThrow(()-> new RuntimeException("Task not found"));
        taskRepository.deleteById(taskId);
        return "deleted";
    }

    @Override
    public TaskDto fetchSingleTask(Long id) {

        TaskDto taskDto = new TaskDto();
       TaskEntity taskEntity =  taskRepository.findById(id).orElseThrow(()-> new RuntimeException("Task not found"));

        TaskMapper.mapToTaskDto(taskDto,taskEntity);

        try{
            if(taskEntity.getCreatedBy()!=null){
                taskDto.setCreatedBy( userFeignClient.fetchUserById(taskEntity.getAssignedTo(),"internal").getBody()
                );
            }
            if(taskEntity.getUpdatedBy()!=null){
                taskDto.setUpdatedBy( userFeignClient.fetchUserById(taskEntity.getUpdatedBy(),"internal").getBody()
                );
            }

            Optional.ofNullable(taskEntity.getAssignedFrom()).ifPresent( userId->
                    taskDto.setAssignedFrom( userFeignClient.fetchUserById(userId,"internal").getBody())
            );

            Optional.ofNullable(taskEntity.getAssignedTo()).ifPresent( userId->
                    taskDto.setAssignedToObj( userFeignClient.fetchUserById(userId,"internal").getBody())
            );

        }catch (Exception e){

        }
        return taskDto;
    }


    @Override
    public TaskCountDto taskCountByStatus() {
            List<Object[]>  taskCount =   taskRepository.countByStatusGrouped();
            TaskCountDto taskCountDto = new TaskCountDto();
            for(Object[] row : taskCount){
                TaskStatusEnum status = (TaskStatusEnum) row[0];
                Long count = (Long) row[1];
                if(status.equals(TaskStatusEnum.OPEN)){
                    taskCountDto.setOpen(count.intValue());
                }

              log.info  ("Status: " + status + ", Count: " + count);
        }

        return taskCountDto;
    }
}
