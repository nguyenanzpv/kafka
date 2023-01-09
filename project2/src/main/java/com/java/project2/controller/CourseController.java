package com.java.project2.controller;

import com.java.project2.dto.CourseDTO;
import com.java.project2.dto.PageDTO;
import com.java.project2.dto.UserRoleDTO;
import com.java.project2.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/course")
public class CourseController {
    @Autowired
    CourseService courseService;

    @GetMapping("/new")
    public String add(){
        return "course/add.html";
    }

    @PostMapping("/new")
    public String add(@ModelAttribute CourseDTO courseDTO) throws IllegalStateException, IOException {
        courseService.create(courseDTO);
        return "redirect:/course/search";
    }

    @GetMapping("/search")
    public String search(
            @RequestParam(name ="name", required=false) String name,
            @RequestParam(name = "size", required = false) Integer size,
            @RequestParam(name = "page", required = false) Integer page,
                          Model model )
    {
        size = size == null ? 10 : size;
        page = page == null ? 0 : page;
        name = name == null ?"" : name;

        PageDTO<CourseDTO> pageRS = courseService.searchByName("%"+name+"%",page,size);

        model.addAttribute("totalPage",pageRS.getTotalPages());
        model.addAttribute("count", pageRS.getTotalElements());
        model.addAttribute("courses", pageRS.getContents());

        // luu lai du lieu set sang view lai
        model.addAttribute("name", name);

        model.addAttribute("page", page);
        model.addAttribute("size", size);
        return "course/search.html";

    }

    @GetMapping("/edit") // ?id=1
    public String edit(@RequestParam("id") int id, Model model) {
        model.addAttribute("course", courseService.getById(id));
        return "course/edit.html";
    }

    @PostMapping("/edit")
    public String edit(@ModelAttribute CourseDTO courseDTO) throws IllegalStateException, IOException {
        courseService.update(courseDTO);
        return "redirect:/course/search";
    }

    //@DeleteMapping("/delete") //?id=
    @GetMapping("/delete")
    public String delete(@RequestParam("id") int id) {
        courseService.delete(id);
        return "redirect:/course/search";
    }

    //@DeleteMapping("/delete-all") //?ids=1,2,3
    @GetMapping("/delete-all")
    public String deleteAll(@RequestParam(value = "cid[]") List<Integer> ids) {
        courseService.deleteAll(ids);
        return "redirect:/course/search";
    }

    @GetMapping("/get/{id}")//get/10
    public String get(@PathVariable("id") int id, Model model) {
        model.addAttribute("course", courseService.getById(id));
        return "course/view.html";
    }
}
