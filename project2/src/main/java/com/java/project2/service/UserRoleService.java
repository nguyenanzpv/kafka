package com.java.project2.service;

import com.java.project2.dto.PageDTO;
import com.java.project2.dto.UserRoleDTO;

import java.util.List;

public interface UserRoleService {
    public void create(UserRoleDTO userRoleDTO);
    public void update(UserRoleDTO userRoleDTO);
    public void delete(int id);
    public void deleteAll(List<Integer> ids);
    public void deleteByUserId(int userId);
    public UserRoleDTO getById(int id);
    public PageDTO<UserRoleDTO> searchByUserId(int userId, int page, int size);
    public PageDTO<UserRoleDTO> searchByRole(String role, int page, int size);

    public PageDTO<UserRoleDTO> searchByUserIdRole(int userId,String role, int page, int size);
}
