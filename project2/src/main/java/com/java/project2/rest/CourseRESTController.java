package com.java.project2.rest;

import com.java.project2.dto.*;
import com.java.project2.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/course")
public class CourseRESTController {
    @Autowired
    CourseService courseService;

    @PostMapping("/new")
    //@ResponseStatus(value = HttpStatus.CREATED)
    public ResponseDTO<CourseDTO> add(@ModelAttribute CourseDTO courseDTO){
        courseService.create(courseDTO);
        return ResponseDTO.<CourseDTO>builder().status(200).data(courseDTO).build();
    }

    @PostMapping("/new-json")
    //@ResponseStatus(value = HttpStatus.CREATED)
    public ResponseDTO<CourseDTO> create(@RequestBody CourseDTO courseDTO){
        courseService.create(courseDTO);
        return ResponseDTO.<CourseDTO>builder().status(200).data(courseDTO).build();
    }

    @DeleteMapping("/delete") //delete?id=10
    public ResponseDTO<Void> delete(@RequestParam("id") int id ){
        courseService.delete(id);
        return ResponseDTO.<Void>builder().status(200).build();
    }

    @GetMapping("/get/{id}") ///get/10
    public ResponseDTO<CourseDTO> get(@PathVariable("id") int id){
        CourseDTO courseDTO = courseService.getById(id);
        return ResponseDTO.<CourseDTO>builder().status(200).data(courseDTO).build();
        //jackson add on, gson
    }

    @PostMapping("/search")
    public ResponseDTO<PageDTO<CourseDTO>> search(
            @RequestParam(name ="name", required=false) String name,
            @RequestParam(name = "size", required = false) Integer size,
            @RequestParam(name = "page", required = false) Integer page,
            Model model
    )
    {
        size = size == null ? 10 : size;
        page = page == null ? 0 : page;

        PageDTO<CourseDTO> pageRS = null;
        pageRS = courseService.searchByName(name,page,size);

        return ResponseDTO.<PageDTO<CourseDTO>>builder().status(200).data(pageRS).build();
    }

    @PostMapping("/edit")
    public ResponseDTO<CourseDTO> edit(@ModelAttribute CourseDTO courseDTO) throws IllegalStateException, IOException {
        courseService.update(courseDTO);
        return ResponseDTO.<CourseDTO>builder().status(200).data(courseDTO)
                .build();
    }

    @PostMapping("/edit-json")
    //@ResponseStatus(value= HttpStatus.CREATED)
    public ResponseDTO<CourseDTO> editJson(@RequestBody CourseDTO courseDTO){
        courseService.update(courseDTO);
        return ResponseDTO.<CourseDTO>builder().status(200).data(courseDTO)
                .build();
    }
}
