package com.java.project2.service;

import com.java.project2.dto.CourseDTO;
import com.java.project2.dto.PageDTO;
import com.java.project2.dto.UserRoleDTO;

import java.util.List;

public interface CourseService {
    public void create(CourseDTO courseDTO);
    public void update(CourseDTO courseDTO);
    public void delete(int id);
    public void deleteAll(List<Integer> ids);
    public CourseDTO getById(int id);
    public PageDTO<CourseDTO> searchByName(String name, int page, int size);
    public List<CourseDTO> getAll();
}
