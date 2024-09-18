package com.assinger.taskservice.entity;



import com.assinger.taskservice.enums.AssignedTypeEnum;
import com.assinger.taskservice.enums.TaskStatusEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name="tasks")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class TaskEntity extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="task_id")
    private Long taskId;

    @Column(name="subject")
    @NotEmpty @NotNull
    private String subject;

    @Column(name="status")
    @Enumerated(EnumType.STRING)
    @NotNull
    private TaskStatusEnum status;

    @Column(name="remarks")
    private String remarks;

    @Column(name="assigned_to")
    private Long assignedTo;

    @Column(name="assigned_to_type")
    @Enumerated(EnumType.STRING)
    private AssignedTypeEnum assignedToType;

    @Column(name="assigned_from")
    private Long assignedFrom;

}
