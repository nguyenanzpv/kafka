package com.java.project2.rest;

import com.java.project2.dto.PageDTO;
import com.java.project2.dto.ResponseDTO;
import com.java.project2.dto.UserDTO;
import com.java.project2.dto.UserRoleDTO;
import com.java.project2.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/api/user-role")
public class UserRoleRESTController {
    @Autowired
    UserRoleService userRoleService;

    @PostMapping("/new")
    //@ResponseStatus(value= HttpStatus.CREATED)
    public ResponseDTO<Void> add(@ModelAttribute UserRoleDTO userRoleDTO){
        userRoleService.create(userRoleDTO);
        return ResponseDTO.<Void>builder().status(200).build();
    }

    @PostMapping("/new-json")
    //@ResponseStatus(value= HttpStatus.CREATED)
    public ResponseDTO<Void> create(@RequestBody UserRoleDTO userRoleDTO){
        userRoleService.create(userRoleDTO);
        return ResponseDTO.<Void>builder().status(200).build();
    }

    @DeleteMapping("/delete") //delete?id=10
    public ResponseDTO<Void> delete(@RequestParam("id") int id ){
        userRoleService.delete(id);
        return ResponseDTO.<Void>builder().status(200).build();
    }

    @GetMapping("/get/{id}") ///get/10
    public ResponseDTO<UserRoleDTO> get(@PathVariable("id") int id){
        UserRoleDTO userRoleDTO = userRoleService.getById(id);
        return ResponseDTO.<UserRoleDTO>builder().status(200).data(userRoleDTO).build();
        //jackson add on, gson
    }

    @PostMapping("/search")
    public ResponseDTO<PageDTO<UserRoleDTO>> search(
            @RequestParam(name="id", required = false) Integer id,
            @RequestParam(name ="role", required=false) String role,
            @RequestParam(name ="userId", required=false) Integer userId,
            @RequestParam(name = "size", required = false) Integer size,
            @RequestParam(name = "page", required = false) Integer page,
            Model model
    )
    {
        size = size == null ? 10 : size;
        page = page == null ? 0 : page;
        role = role == null ?"" : role;
        userId = userId == null ? null : userId;
        PageDTO<UserRoleDTO> pageRS = null;
        if(userId == null){
            pageRS = userRoleService.searchByRole("%"+role+"%",page,size);
        }
        else if(role == "" || role.isEmpty()){
            pageRS = userRoleService.searchByUserId(userId,page,size);
        } else if(role != "" && userId != null){
            pageRS = userRoleService.searchByUserIdRole(userId,role,page,size);
        }
        return ResponseDTO.<PageDTO<UserRoleDTO>>builder().status(200).data(pageRS).build();
    }

    @PostMapping("/edit")
    public ResponseDTO<Void> edit(@ModelAttribute UserRoleDTO userRoleDTO) throws IllegalStateException, IOException {
        userRoleService.update(userRoleDTO);
        return ResponseDTO.<Void>builder().status(200)
                .build();
    }

    @PostMapping("/edit-json")
    //@ResponseStatus(value= HttpStatus.CREATED)
    public ResponseDTO<Void> editJson(@RequestBody UserRoleDTO userRoleDTO){
        userRoleService.update(userRoleDTO);
        return ResponseDTO.<Void>builder().status(200).build();
    }
}
