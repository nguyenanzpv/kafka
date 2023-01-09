package com.java.project2.controller;

import com.java.project2.dto.PageDTO;
import com.java.project2.dto.StudentDTO;
import com.java.project2.dto.UserDTO;
import com.java.project2.entity.Student;
import com.java.project2.service.StudentService;
import com.java.project2.service.UserService;
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
@RequestMapping("/student")
public class StudentController {
    @Autowired
    StudentService studentService;
    @Autowired
    UserService userService;

    @GetMapping("/new")
    public String add(Model model){
        model.addAttribute("userList",userService.getAll());
        return "student/add.html";
    }

    @PostMapping("/new")
    public String add(@ModelAttribute StudentDTO studentDTO)  throws IOException{
        studentService.create(studentDTO);
        return "redirect:/student/search";
    }

    @GetMapping("/search")
    public String search(
            @RequestParam(name ="name", required=false) String name,
            @RequestParam(name ="studentCode", required=false) String studentCode,
            @RequestParam(name = "size", required = false) Integer size,
            @RequestParam(name = "page", required = false) Integer page,
            Model model
    )
    {
        size = size == null ? 10 : size;
        page = page == null ? 0 : page;
        studentCode = studentCode == null ?"" : studentCode;

        PageDTO<StudentDTO> pageRS = studentService.search("%"+name+"%","%"+studentCode+"%",page,size);
        model.addAttribute("totalPage",pageRS.getTotalPages());
        model.addAttribute("count", pageRS.getTotalElements());
        model.addAttribute("students", pageRS.getContents());

        // luu lai du lieu set sang view lai
        model.addAttribute("studentCode", studentCode);

        model.addAttribute("page", page);
        model.addAttribute("size", size);

        model.addAttribute("userList",userService.getAll());

        return "student/search.html";
    }

    @GetMapping("/edit") // ?id=1
    public String edit(@RequestParam("id") int id, Model model) {
        model.addAttribute("student", studentService.getById(id));
        return "student/edit.html";
    }

    @PostMapping("/edit")
    public String edit(@ModelAttribute StudentDTO studentDTO) throws IllegalStateException, IOException {
        studentService.update(studentDTO);
        return "redirect:/student/search";
    }

    //@DeleteMapping("/delete") //?id=
    @GetMapping("/delete")
    public String delete(@RequestParam("id") int id) {
        studentService.deleteById(id);
        return "redirect:/student/search";
    }

    @GetMapping("/delete-code")
    public String deleteCode(@RequestParam("studentCode") String studentCode) {
        studentService.deleteByCode(studentCode);
        return "redirect:/student/search";
    }

    //@DeleteMapping("/delete-all") //?ids=1,2,3
    @GetMapping("/delete-all")
    public String deleteAll(@RequestParam(value = "cid[]") List<Integer> ids) {
        studentService.deleteAll(ids);
        return "redirect:/student/search";
    }

    @GetMapping("/get/{id}")//get/10
    public String get(@PathVariable("id") int id, Model model) {
        StudentDTO studentDTO = studentService.getById(id);
        model.addAttribute("student",studentDTO );
        model.addAttribute("userList", userService.getAll());
        return "student/view.html";
    }
}
