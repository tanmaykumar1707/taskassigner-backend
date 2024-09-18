package com.assinger.taskservice.dto;


import com.assinger.taskservice.enums.AssignedTypeEnum;
import com.assinger.taskservice.enums.TaskStatusEnum;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.apache.catalina.User;

import java.time.LocalDateTime;

@Data
public class TaskDto extends AuditBaseDto {

    private Long taskId;

    @NotEmpty(message = "Taks Subject can not be null or empty")
    private String subject;

   // @NotEmpty(message="Task Status can not be null or empty") --CAUSE ERROR ,NOT EMPTY NOT ALLOWED
   // @NotNull(message = "Task Status can not be null or empty")
    private TaskStatusEnum status;

    private String remarks;

   // @NotEmpty(message="Assinged to id is required")
    @NotNull(message ="Assigned to id is required")
    private Long assignedTo;

    private UserDto assignedToObj;

    private AssignedTypeEnum assignedToType;

    private UserDto assignedFrom;


}
