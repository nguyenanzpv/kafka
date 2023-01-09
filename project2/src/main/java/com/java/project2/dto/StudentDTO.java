package com.java.project2.dto;

import lombok.Data;

import javax.persistence.*;

@Data
public class StudentDTO {
    private Integer id;
    private String studentCode;
    //private String userName;
    //private Integer userId;
    private UserDTO user;
}
