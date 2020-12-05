package com.my.jasuil.data.entities;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = { "email" }))
public class UserInfo {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;
    private String name;
    private String password;
    private String email;
    private String sessionId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
