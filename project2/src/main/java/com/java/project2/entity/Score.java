package com.java.project2.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Score {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private double score;

    @ManyToOne
    //@JoinColumn(name="student_user_id" )
    private Student student;

    @ManyToOne
    //@JoinColumn(name="course_id" )
    private Course course;
}
