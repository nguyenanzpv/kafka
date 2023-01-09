package com.java.project2.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "group_of_user")
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @ManyToMany
    @JoinTable(name="user_group", joinColumns=
    @JoinColumn(name="group_id"),
            inverseJoinColumns = @JoinColumn(name="user_id")
    )
    List<User> users;
}
