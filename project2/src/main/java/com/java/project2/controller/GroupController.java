package com.java.project2.controller;

import com.java.project2.dto.GroupDTO;
import com.java.project2.dto.PageDTO;
import com.java.project2.dto.UserRoleDTO;
import com.java.project2.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/group")
public class GroupController {
    @Autowired
    GroupService groupService;

    @GetMapping("/new")
    public String add(){
        return "group/add.html";
    }

    @PostMapping("/new")
    public String add(@ModelAttribute GroupDTO groupDTO) throws IllegalStateException, IOException {
        groupService.create(groupDTO);
        return "redirect:/group/search";
    }

    @GetMapping("/search")
    public String search(
            @RequestParam(name="id", required = false) Integer id,
            @RequestParam(name ="name", required=false) String name,
            @RequestParam(name ="userId", required=false) Integer userId,
            @RequestParam(name = "size", required = false) Integer size,
            @RequestParam(name = "page", required = false) Integer page,
            Model model
    )
    {
        size = size == null ? 10 : size;
        page = page == null ? 0 : page;
        name = name == null ?"" : name;
        userId = userId == null ? null : userId;
        PageDTO<GroupDTO> pageRS = null;

        if(id == null){
            pageRS = groupService.searchByName("%"+name+"%",page,size);

        }
        else if(name == "" || name.isEmpty()){
            pageRS = groupService.searchById(id,page,size);

        } else if(name != "" && id != null && userId != null){
            pageRS = groupService.searchByUser(userId,page,size);

        }
        model.addAttribute("totalPage",pageRS.getTotalPages());
        model.addAttribute("count", pageRS.getTotalElements());
        model.addAttribute("groups", pageRS.getContents());

        // luu lai du lieu set sang view lai
        model.addAttribute("id", id);
        model.addAttribute("name", name);
        model.addAttribute("userId", userId);

        model.addAttribute("page", page);
        model.addAttribute("size", size);
        return "group/search.html";
    }

    @GetMapping("/edit") // ?id=1
    public String edit(@RequestParam("id") int id, Model model) {
        model.addAttribute("group", groupService.getById(id));
        return "group/edit.html";
    }

    @PostMapping("/edit")
    public String edit(@ModelAttribute GroupDTO groupDTO) throws IllegalStateException, IOException {
        groupService.update(groupDTO);
        return "redirect:/group/search";
    }

    //@DeleteMapping("/delete") //?id=
    @GetMapping("/delete")
    public String delete(@RequestParam("id") int id) {
        groupService.delete(id);
        return "redirect:/group/search";
    }

    //@DeleteMapping("/delete-all") //?ids=1,2,3
    @GetMapping("/delete-all")
    public String deleteAll(@RequestParam(value = "cid[]") List<Integer> ids) {
        groupService.deleteAll(ids);
        return "redirect:/group/search";
    }

    @GetMapping("/get/{id}")//get/10
    public String get(@PathVariable("id") int id, Model model) {
        model.addAttribute("group", groupService.getById(id));
        return "group/view.html";
    }
}
