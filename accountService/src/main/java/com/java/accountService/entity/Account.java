package com.java.accountService.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="account")
@Data
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="name")
    private String name;

    @Column(name="email")
    private String email;

    private String password;

    @Column(name="username", unique = true)
    private String username;

}
