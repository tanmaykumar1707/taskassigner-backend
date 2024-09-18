package com.assinger.taskservice.repository;

import com.assinger.taskservice.entity.TaskEntity;
import com.assinger.taskservice.entity.ThreadEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ThreadRepository extends JpaRepository<ThreadEntity,Long> {

   List<ThreadEntity> findByTask_TaskId(Long taskId);

}
