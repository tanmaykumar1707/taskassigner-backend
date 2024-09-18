package com.assinger.taskservice.service;

import com.assinger.taskservice.dto.ThreadDto;
import com.assinger.taskservice.entity.ThreadEntity;

import java.util.List;

public interface IThreadService {

    void saveThread(Long taskId, ThreadDto threadDto);

    List<ThreadDto> findThreadByTask(Long taskId);

    void deleteSingleThread(Long threadId);





}
