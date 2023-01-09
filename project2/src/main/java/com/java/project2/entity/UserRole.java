package com.java.project2.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class UserRole {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne //nhieu userrole to one user
    //@JoinColumn(name="user_id" )
    private User user;

    private String role; //ADMIN,MEMBER
}
