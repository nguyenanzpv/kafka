package com.java.project2.dto;

import lombok.Data;

@Data
public class UserRoleDTO {
    private Integer id;
    private Integer userId;  //Integer thay cho User de ko bi truy van hai chieu long nhau
    private String userName;
    private String role;
}
