package com.java.project2.service.impl;

import com.java.project2.dto.PageDTO;
import com.java.project2.dto.UserDTO;
import com.java.project2.dto.UserRoleDTO;
import com.java.project2.entity.User;
import com.java.project2.entity.UserRole;
import com.java.project2.repo.UserRepo;
import com.java.project2.repo.UserRoleRepo;
import com.java.project2.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepo userRepo;

    @Autowired
    UserRoleRepo userRoleRepo;

    @Autowired
    ModelMapper modelMapper;

    @Override
    @Transactional
    public void create(UserDTO userDTO) {
        User u = new ModelMapper().map(userDTO,User.class);
        //convert dto - entity
        /*u.setName(user.getName());
        u.setUsername(user.getUsername());
        u.setBirthdate(user.getBirthdate());
        u.setPassword(user.getPassword());
        u.setAvatar(user.getAvatar());*/

        userRepo.save(u);

        //save userrole
        List<UserRoleDTO> userRoleDTOS = userDTO.getUserRoles();
        {
            for (UserRoleDTO userRoleDTO : userRoleDTOS){
                if(userRoleDTO.getRole()!=null){
                    //save to db
                    UserRole userRole = new UserRole();
                    userRole.setUser(u);
                    userRole.setRole(userRoleDTO.getRole());
                    userRoleRepo.save(userRole);
                }
            }
        }
    }

    @Override
    @Transactional
    public UserDTO getById(int id) {
        User user = userRepo.findById(id).orElseThrow(NoResultException::new); //java8 lambda
        /*UserDTO userDto = new UserDTO();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setUsername(user.getUsername());
        userDto.setPassword(user.getPassword());
        userDto.setBirthdate(user.getBirthdate());
        userDto.setAvatar(user.getAvatar());
        userDto.setCreatedAt(user.getCreatedAt());*/
        UserDTO userDto = new ModelMapper().map(user,UserDTO.class);
        return userDto;
    }

    @Override
    @Transactional
    public void deleteById(int id) {
        User user = userRepo.findById(id).orElseThrow(NoResultException::new); //java8 lambda

        if(user!=null){
            userRoleRepo.deleteByUserId(user.getId());
            userRepo.deleteById(user.getId());
        }

    }

    @Override
    @Transactional
    public void deleteAll(List<Integer> ids) {
        for(Integer id : ids){
            userRoleRepo.deleteByUserId(id);
        }
        userRepo.deleteAllById(ids);
    }

    @Override
    @Transactional
    public void update(UserDTO userDTO) {
        Optional<User> u = userRepo.findById(userDTO.getId());
        if(u!=null){
         /*   modelMapper.typeMap(UserDTO.class,User.class)
                    .addMappings(mapper -> mapper.skip(User::setPassword)).map(userDTO,u.get());*/
            u.get().setName(userDTO.getName());
            u.get().setAvatar(userDTO.getAvatar());
            u.get().setBirthdate(userDTO.getBirthdate());
            u.get().setUsername(userDTO.getUsername());
            userRepo.save(u.get());
        }
    }

    @Override
    @Transactional
    public void updatePassword(UserDTO userDTO) {
        User user = userRepo.findById(userDTO.getId()).orElseThrow(NoResultException::new);
        user.setPassword(userDTO.getPassword());

        userRepo.save(user);
    }

    @Override
    @Transactional
    public PageDTO<UserDTO> searchByName(String name, int page, int size) {
        Pageable pageable = PageRequest.of(page,size);
        Page<User> pageRS = userRepo.searchByName("%"+name+"%", pageable);
        PageDTO<UserDTO> pageDTO = new PageDTO<>();
        pageDTO.setTotalPages(pageRS.getTotalPages());
        pageDTO.setTotalElements(pageRS.getTotalElements());

        List<UserDTO> userDTOs = new ArrayList<>();
        for(User user:pageRS.getContent()){
            userDTOs.add(new ModelMapper().map(user,UserDTO.class));
        }
        pageDTO.setContents(userDTOs); //set vao pageDto
        return pageDTO;
    }

    @Override
    public List<UserDTO> getAll() {
        List<UserDTO> userDTOS = new ArrayList<>();
        List<User> users = userRepo.findAll();
        for(User u:users){
            UserDTO userDTO = new ModelMapper().map(u,UserDTO.class);
            userDTOS.add(userDTO);
        }
        return userDTOS;
    }
}
