package com.assinger.taskservice.mapper;

import com.assinger.taskservice.dto.ThreadDto;
import com.assinger.taskservice.entity.ThreadEntity;

public class ThreadMapper {

    private ThreadMapper(){

    }

    public static ThreadEntity mapToThreadEntity(ThreadEntity threadEntity, ThreadDto threadDto){
            threadEntity.setSubject(threadDto.getSubject());
            threadEntity.setRemarks(threadDto.getRemarks());
            threadEntity.setFileName(threadDto.getFileName());

        return  threadEntity;
    }

    public static ThreadDto mapToThreadDto( ThreadDto threadDto,ThreadEntity threadEntity){
        threadDto.setThreadId(threadEntity.getThreadId());
        threadDto.setSubject(threadEntity.getSubject());
        threadDto.setRemarks(threadEntity.getRemarks());
        threadDto.setFileName(threadEntity.getFileName());
        threadDto.setCreatedAt(threadEntity.getCreatedAt());
        threadDto.setUpdatedAt(threadEntity.getUpdatedAt());

        return  threadDto;
    }
}
