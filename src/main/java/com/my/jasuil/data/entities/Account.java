package com.my.jasuil.data.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * current account information
 */
@Entity
@Data
public class Account {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;
    @Column(name="service_id", unique = true)
    private String serviceId;
    private String name;
    private String category;
    private String plan;
    private Date premiumIdExpiredAt;
    private Date createdAt;
    private Date updatedAt;
    
}
