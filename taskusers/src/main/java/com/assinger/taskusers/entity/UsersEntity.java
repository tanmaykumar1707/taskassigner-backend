package com.assinger.taskusers.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class UsersEntity extends BaseEntity{


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id")
    private Long userId;

    @Column(name="name")
    private String name;

    @Column(name="email")
    private String email;

    @Column(name="mobile_number")
    private String mobileNumner;

    @Column(name="password")
    private String password;

    @Column(name="enabled")
    private boolean enabled;

    @Column(name="role")
    private String role;

    @Column(name="lastPasswordChangedAt")
    private LocalDateTime lastPasswordChangedAt;

    @Column(name="passwordResetToken")
    private String passwordResetToken;

    @Column(name="passwordResetTokenExpiry")
    private String passwordResetTokenExpiry;



}
