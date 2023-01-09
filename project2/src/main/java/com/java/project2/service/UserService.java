package com.java.project2.service;

import com.java.project2.dto.PageDTO;
import com.java.project2.dto.UserDTO;

import java.util.List;

public interface UserService {
    public void create(UserDTO userDTO);
    public UserDTO getById(int id);
    public void deleteById(int id);
    public void deleteAll(List<Integer> ids);
    public void update(UserDTO userDTO);
    public void updatePassword(UserDTO userDTO);
    public PageDTO<UserDTO> searchByName(String name, int page, int size);

    public List<UserDTO> getAll();

}
