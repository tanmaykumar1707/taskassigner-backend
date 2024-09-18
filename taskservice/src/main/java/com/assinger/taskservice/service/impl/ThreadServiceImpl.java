package com.assinger.taskservice.service.impl;

import com.assinger.taskservice.dto.ThreadDto;
import com.assinger.taskservice.dto.UserDto;
import com.assinger.taskservice.entity.TaskEntity;
import com.assinger.taskservice.entity.ThreadEntity;
import com.assinger.taskservice.exception.TaskNotFoundException;
import com.assinger.taskservice.exception.ThreadNotFoundException;
import com.assinger.taskservice.mapper.ThreadMapper;
import com.assinger.taskservice.repository.TaskRepository;
import com.assinger.taskservice.repository.ThreadRepository;
import com.assinger.taskservice.service.ITaskService;
import com.assinger.taskservice.service.IThreadService;
import com.assinger.taskservice.service.client.UserFeignClient;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ThreadServiceImpl implements IThreadService {

    private TaskRepository taskRepository;
    private ThreadRepository threadRepository;
    private UserFeignClient userFeignClient;


    @Override
    public void saveThread(Long taskId, ThreadDto threadDto) {
     TaskEntity taskEntity = taskRepository.findById(taskId)
             .orElseThrow(() -> new TaskNotFoundException("Task not found with id "+taskId.toString()));

     ThreadEntity threadEntity = new ThreadEntity();
     ThreadMapper.mapToThreadEntity(threadEntity,threadDto);
     threadEntity.setTask(taskEntity);

     threadRepository.save(threadEntity);
    }

    @Override
    public List<ThreadDto> findThreadByTask(Long taskId) {
        TaskEntity taskEntity = taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException("Task not found with id "+taskId.toString()));
       List<ThreadEntity> threadEntityList =  threadRepository.findByTask_TaskId(taskEntity.getTaskId());
       List<ThreadDto> threadDtoList = new ArrayList<>();
       threadEntityList.forEach((threadEntity -> {
           ThreadDto threadDto  =new ThreadDto();
           ThreadMapper.mapToThreadDto(threadDto,threadEntity);
           try{
               if(threadEntity.getCreatedBy()!=null){
                   UserDto userDtoTemp =  userFeignClient.fetchUserById(taskEntity.getAssignedTo(),"internal").getBody();
                    threadDto.setCreatedBy(userDtoTemp);
               }
               if(threadEntity.getUpdatedBy()!=null){
                   UserDto userDtoTemp =  userFeignClient.fetchUserById(taskEntity.getAssignedTo(),"internal").getBody();
                   threadDto.setUpdatedBy(userDtoTemp);
               }

           }catch (Exception e){

           }
           threadDtoList.add(threadDto);
       }));
        return  threadDtoList;
    }

    @Override
    public void deleteSingleThread(Long threadId) {

      ThreadEntity threadEntity =   threadRepository.findById(threadId)
                .orElseThrow(()-> new ThreadNotFoundException("Thread not found with threadid "+ threadId.toString()));

      threadRepository.deleteById(threadEntity.getThreadId());
    }
}
