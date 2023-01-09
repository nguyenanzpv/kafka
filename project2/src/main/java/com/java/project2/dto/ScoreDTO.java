package com.java.project2.dto;

import com.java.project2.entity.Course;
import com.java.project2.entity.Student;
import lombok.Data;

import javax.persistence.ManyToOne;

@Data
public class ScoreDTO {
    private Integer id;

    private double score;

  /*  private Integer studentId;

    private String studentCode;

    private Integer courseId;

    private String courseName;*/
    private StudentDTO student;
    private CourseDTO course;
}
