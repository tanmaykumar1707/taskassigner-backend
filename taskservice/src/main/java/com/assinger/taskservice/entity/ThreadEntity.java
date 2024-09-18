package com.assinger.taskservice.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Getter @Setter @ToString
@AllArgsConstructor @NoArgsConstructor
@Table(name="task_threads")
public class ThreadEntity extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="thread_id")
    private Long threadId;

    @ManyToOne(fetch = FetchType.LAZY,optional = false) // defalut fetch type is EAGER
    @JoinColumn(name="task_id",nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private TaskEntity task;

    @NotEmpty
    @Column(name = "subject")
    private String subject;

    @Column(name="remarks")
    private String remarks;

    @Column(name="file_name")
    private String fileName;


}
