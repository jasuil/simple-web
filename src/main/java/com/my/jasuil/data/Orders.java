package com.my.jasuil.data;

import javax.persistence.*;

@Entity
public class Orders {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    @ManyToOne
    @JoinColumn(name="service_id")
    Account account;
}
