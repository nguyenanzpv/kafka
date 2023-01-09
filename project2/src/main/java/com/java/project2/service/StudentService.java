package com.java.project2.service;

import com.java.project2.dto.PageDTO;
import com.java.project2.dto.StudentDTO;
import com.java.project2.dto.UserDTO;

import java.util.List;

public interface StudentService {
    public void create(StudentDTO studentDTO);
    public StudentDTO getById(int userId);
    public void deleteByCode(String studentCode);
    public void deleteById(int userId);
    public void deleteAll(List<Integer> ids);
    public void update(StudentDTO studentDTO);
    public PageDTO<StudentDTO> search(String name, String studentCode, int page, int size);

    public List<StudentDTO> getAll();
}
