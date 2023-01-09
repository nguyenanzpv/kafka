package com.java.project2.service.impl;

import com.java.project2.dto.GroupDTO;
import com.java.project2.dto.PageDTO;
import com.java.project2.dto.UserDTO;
import com.java.project2.dto.UserRoleDTO;
import com.java.project2.entity.Group;
import com.java.project2.entity.User;
import com.java.project2.entity.UserRole;
import com.java.project2.repo.GroupRepo;
import com.java.project2.repo.UserRepo;
import com.java.project2.service.GroupService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.NoResultException;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class GroupServiceImpl implements GroupService {
    @Autowired
    GroupRepo groupRepo;
    @Autowired
    UserRepo userRepo;

    @Override
    @Transactional
    public void create(GroupDTO groupDTO) {
        Group group = new Group();
        group.setName(groupDTO.getName());

        List<User> users = new ArrayList<>();

        for(UserDTO userDTO: groupDTO.getUsers()){
            User user = userRepo.findById(userDTO.getId()).orElseThrow(NoResultException::new);
            users.add(user);
        }
        group.setUsers(users);
        groupRepo.save(group);
    }

    @Override
    @Transactional
    public void update(GroupDTO groupDTO) {
        Group group = groupRepo.findById(groupDTO.getId()).orElseThrow(NoResultException::new);
        group.setName(groupDTO.getName());

        //neu da co du lieu bang trung gian (user_group)
        if (group.getUsers() != null) {
            group.getUsers().clear();

            //List<User> users = group.getUsers();
            for (UserDTO userDTO : groupDTO.getUsers()) {
                User user = userRepo.findById(userDTO.getId()).orElseThrow(NoResultException::new);
                group.getUsers().add(user);
            }
        }else{ //them moi
            List<User> users = new ArrayList<>();

            for(UserDTO userDTO: groupDTO.getUsers()){
                User user = userRepo.findById(userDTO.getId()).orElseThrow(NoResultException::new);
                users.add(user);
            }
            group.setUsers(users);
        }
        groupRepo.save(group);
    }

    @Override
    @Transactional
    public void delete(int id) {
        groupRepo.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteAll(List<Integer> ids) {
        groupRepo.deleteAllById(ids);
    }

    @Override
    @Transactional
    public void deleteByName(String name) {
        groupRepo.deleteByName(name);
    }

    @Override
    @Transactional
    public GroupDTO getById(int id) {
       Group group = groupRepo.findById(id).orElseThrow(NoResultException::new);
       return new ModelMapper().map(group,GroupDTO.class);
    }

    @Override
    @Transactional
    public PageDTO<GroupDTO> searchById(int id, int page, int size) {
        Pageable pageable = PageRequest.of(page,size);

        Page<Group> pageRS = groupRepo.searchById(id,pageable);

        PageDTO<GroupDTO> pageDTO = new PageDTO<GroupDTO>();
        pageDTO.setTotalPages(pageRS.getTotalPages());
        pageDTO.setTotalElements(pageRS.getTotalElements());

        List<GroupDTO> groupDTOS = new ArrayList<>();
        for(Group group : pageRS.getContent()){
            groupDTOS.add(new ModelMapper().map(group,GroupDTO.class));
        }

        pageDTO.setContents(groupDTOS);
        return pageDTO;
    }

    @Override
    @Transactional
    public PageDTO<GroupDTO> searchByName(String name, int page, int size) {
        Pageable pageable = PageRequest.of(page,size);

        Page<Group> pageRS = groupRepo.searchByName("%"+name+"%",pageable);

        PageDTO<GroupDTO> pageDTO = new PageDTO<GroupDTO>();
        pageDTO.setTotalPages(pageRS.getTotalPages());
        pageDTO.setTotalElements(pageRS.getTotalElements());

        List<GroupDTO> groupDTOS = new ArrayList<>();
        for(Group group : pageRS.getContent()){
            groupDTOS.add(new ModelMapper().map(group,GroupDTO.class));
        }

        pageDTO.setContents(groupDTOS);
        return pageDTO;
    }

    @Override
    @Transactional
    public PageDTO<GroupDTO> searchByUser(int userId, int page, int size) {
        Pageable pageable = PageRequest.of(page,size);

        Page<Group> pageRS = groupRepo.searchByUser(userId,pageable);

        PageDTO<GroupDTO> pageDTO = new PageDTO<GroupDTO>();
        pageDTO.setTotalPages(pageRS.getTotalPages());
        pageDTO.setTotalElements(pageRS.getTotalElements());

        List<GroupDTO> groupDTOS = new ArrayList<>();
        for(Group group : pageRS.getContent()){
            groupDTOS.add(new ModelMapper().map(group,GroupDTO.class));
        }

        pageDTO.setContents(groupDTOS);
        return pageDTO;
    }
}
