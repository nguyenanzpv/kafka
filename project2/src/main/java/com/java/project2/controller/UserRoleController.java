package com.java.project2.controller;

import com.java.project2.dto.PageDTO;
import com.java.project2.dto.UserDTO;
import com.java.project2.dto.UserRoleDTO;
import com.java.project2.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.util.List;

@Controller
@RequestMapping("/user-role")
public class UserRoleController {
    @Autowired
    UserRoleService userRoleService;

    @GetMapping("/new")
    public String add(){
        return "user-role/add.html";
    }

    @PostMapping("/new")
    public String add(@ModelAttribute UserRoleDTO u) throws IllegalStateException, IOException {
        userRoleService.create(u);
        return "redirect:/user-role/search";
    }

    @GetMapping("/search")
    public String search(
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
        role = role == null ?"%%" : role;
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
        model.addAttribute("totalPage",pageRS.getTotalPages());
        model.addAttribute("count", pageRS.getTotalElements());
        model.addAttribute("roles", pageRS.getContents());

        // luu lai du lieu set sang view lai
        model.addAttribute("id", id);
        model.addAttribute("role", role);
        model.addAttribute("userId", userId);

        model.addAttribute("page", page);
        model.addAttribute("size", size);
        return "user-role/search.html";
    }

    @GetMapping("/edit") // ?id=1
    public String edit(@RequestParam("id") int id, Model model) {
        model.addAttribute("userRole", userRoleService.getById(id));
        return "user-role/edit.html";
    }

    @PostMapping("/edit")
    public String edit(@ModelAttribute UserRoleDTO userRoleDTO) throws IllegalStateException, IOException {
        userRoleService.update(userRoleDTO);
        return "redirect:/user-role/search";
    }

    //@DeleteMapping("/delete") //?id=
    @GetMapping("/delete")
    public String delete(@RequestParam("id") int id) {
        userRoleService.delete(id);
        return "redirect:/user-role/search";
    }

    //@DeleteMapping("/delete-all") //?ids=1,2,3
    @GetMapping("/delete-all")
    public String deleteAll(@RequestParam(value = "cid[]") List<Integer> ids) {
        userRoleService.deleteAll(ids);
        return "redirect:/user-role/search";
    }

    @GetMapping("/get/{id}")//get/10
    public String get(@PathVariable("id") int id, Model model) {
        model.addAttribute("role", userRoleService.getById(id));
        return "user-role/view.html";
    }
}
