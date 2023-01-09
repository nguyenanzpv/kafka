package com.java.project2.service;

import com.java.project2.dto.GroupDTO;
import com.java.project2.dto.PageDTO;
import com.java.project2.dto.UserRoleDTO;

import java.util.List;

public interface GroupService {
    public void create(GroupDTO groupDTO);
    public void update(GroupDTO groupDTO);
    public void delete(int id);
    public void deleteAll(List<Integer> ids);
    public void deleteByName(String name);
    public GroupDTO getById(int id);
    public PageDTO<GroupDTO> searchById(int id, int page, int size);
    public PageDTO<GroupDTO> searchByName(String name, int page, int size);
    public PageDTO<GroupDTO> searchByUser(int userId, int page, int size);
}
