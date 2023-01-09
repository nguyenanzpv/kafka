package com.java.project2.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Course {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    private String name;
    @OneToMany(mappedBy = "course",fetch = FetchType.EAGER)
    private List<Score> scores;
}