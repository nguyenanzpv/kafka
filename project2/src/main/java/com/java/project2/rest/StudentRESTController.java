package com.java.project2.rest;

import com.java.project2.dto.PageDTO;
import com.java.project2.dto.ResponseDTO;
import com.java.project2.dto.StudentDTO;
import com.java.project2.dto.UserRoleDTO;
import com.java.project2.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/student")
public class StudentRESTController {
    @Autowired
    StudentService studentService;

    @PostMapping("/new")
    //@ResponseStatus(value = HttpStatus.CREATED)
    public ResponseDTO<StudentDTO> add(@ModelAttribute StudentDTO studentDTO){
        studentService.create(studentDTO);
        return ResponseDTO.<StudentDTO>builder().status(200).data(studentDTO).build();
    }

    @PostMapping("/new-json")
    //@ResponseStatus(value = HttpStatus.CREATED)
    public ResponseDTO<StudentDTO> create(@RequestBody StudentDTO studentDTO){
        studentService.create(studentDTO);
        return ResponseDTO.<StudentDTO>builder().status(200).data(studentDTO).build();
    }

    @DeleteMapping("/delete") //delete?id=10
    public ResponseDTO<Void> delete(@RequestParam("id") int id ){
        studentService.deleteById(id);
        return ResponseDTO.<Void>builder().status(200).build();
    }

    @GetMapping("/get/{id}") ///get/10
    public ResponseDTO<StudentDTO> get(@PathVariable("id") int id){
        StudentDTO studentDTO =  studentService.getById(id);
        return ResponseDTO.<StudentDTO>builder().status(200).data(studentDTO).build();
        //jackson add on, gson
    }

    @PostMapping("/search")
    public ResponseDTO<PageDTO<StudentDTO>> search(
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
        name = name == null ?"" : name;

        PageDTO<StudentDTO> pageRS = null;
        return ResponseDTO.<PageDTO<StudentDTO>>builder().status(200).data(studentService.search(name,studentCode,page,size)).build();
    }

    @PostMapping("/edit")
    public ResponseDTO<StudentDTO> edit(@ModelAttribute StudentDTO studentDTO) throws IllegalStateException, IOException {
        studentService.update(studentDTO);
        return ResponseDTO.<StudentDTO>builder().status(200).data(studentDTO)
                .build();
    }

    @PostMapping("/edit-json")
    //@ResponseStatus(value= HttpStatus.CREATED)
    public ResponseDTO<StudentDTO> editJson(@RequestBody StudentDTO studentDTO){
        studentService.update(studentDTO);
        return ResponseDTO.<StudentDTO>builder().status(200).data(studentDTO).build();
    }
}
