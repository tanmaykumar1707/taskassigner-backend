package com.assinger.taskservice.repository;

import com.assinger.taskservice.entity.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<TaskEntity,Long> {

    @Query("SELECT t.status, COUNT(t.status) FROM TaskEntity t GROUP BY t.status")
    List<Object[]> countByStatusGrouped();
}
