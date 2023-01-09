package com.java.project2.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Student {
    @Id
    private Integer id;

    @Column(unique = true)
    private String studentCode;

    @OneToOne(cascade = CascadeType.ALL) //1-1
    @PrimaryKeyJoinColumn() //specifies the primary key that is used as a foreign key to the parent entity
    private User user;

    @OneToMany(mappedBy = "student",fetch = FetchType.EAGER)
    private List<Score> scores;

}