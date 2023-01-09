package com.java.project2.controller;

import com.java.project2.dto.PageDTO;
import com.java.project2.dto.ScoreDTO;
import com.java.project2.dto.StudentDTO;
import com.java.project2.service.CourseService;
import com.java.project2.service.ScoreService;
import com.java.project2.service.StudentService;
import com.java.project2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/score")
public class ScoreController {
    @Autowired
    ScoreService scoreService;
    @Autowired
    StudentService studentService;

    @Autowired
    CourseService courseService;

    @GetMapping("/new")
    public String add(Model model){
        model.addAttribute("studentList",studentService.getAll());
        model.addAttribute("courseList",courseService.getAll());
        return "score/add.html";
    }

    @PostMapping("/new")
    public String add(@ModelAttribute ScoreDTO scoreDTO) throws IllegalStateException, IOException {
        scoreService.create(scoreDTO);
        return "redirect:/score/search";
    }

    @GetMapping("/search")
    public String search(
            @RequestParam(name ="id", required=false) Integer id,
            @RequestParam(name ="score", required=false) Double score,
            @RequestParam(name = "courseId", required = false) Integer courseId,
            @RequestParam(name = "studentId", required = false) Integer studentId,
            @RequestParam(name = "size", required = false) Integer size,
            @RequestParam(name = "page", required = false) Integer page,
            Model model
    )
    {
        size = size == null ? 10 : size;
        page = page == null ? 0 : page;

        PageDTO<ScoreDTO> pageRS= null;
        if(id != null){
            pageRS = scoreService.searchById(id,page,size);
        }else if(score != null){
            pageRS = scoreService.searchByScore(score,page,size);
        }else if(courseId != null){
            pageRS = scoreService.searchByCourseId(courseId,page,size);
        } else if(studentId != null){
            pageRS = scoreService.searchByStudentId(studentId,page,size);
        }else{
            pageRS = scoreService.getAll(page,size);
        }
        model.addAttribute("totalPage",pageRS.getTotalPages());
        model.addAttribute("count", pageRS.getTotalElements());
        model.addAttribute("scores", pageRS.getContents());

        // luu lai du lieu set sang view lai
        model.addAttribute("id", id);
        model.addAttribute("score", score);
        model.addAttribute("page", page);
        model.addAttribute("size", size);

        model.addAttribute("courseList",courseService.getAll());
        model.addAttribute("studentList",studentService.getAll());

        return "score/search.html";
    }

    @GetMapping("/edit") // ?id=1
    public String edit(@RequestParam("id") int id, Model model) {
        model.addAttribute("score", scoreService.getById(id));
        return "score/edit.html";
    }

    @PostMapping("/edit")
    public String edit(@ModelAttribute ScoreDTO scoreDTO) throws IllegalStateException, IOException {
        scoreService.update(scoreDTO);
        return "redirect:/score/search";
    }

    //@DeleteMapping("/delete") //?id=
    @GetMapping("/delete")
    public String delete(@RequestParam("id") int id) {
        scoreService.deleteById(id);
        return "redirect:/score/search";
    }


    //@DeleteMapping("/delete-all") //?ids=1,2,3
    @GetMapping("/delete-all")
    public String deleteAll(@RequestParam(value = "cid[]") List<Integer> ids) {
        scoreService.deleteAll(ids);
        return "redirect:/score/search";
    }

    @GetMapping("/get/{id}")//get/10
    public String get(@PathVariable("id") int id, Model model) {
        ScoreDTO scoreDTO = scoreService.getById(id);
        model.addAttribute("score",scoreDTO );
        model.addAttribute("courseList", courseService.getAll());
        model.addAttribute("studentList", studentService.getAll());
        return "score/view.html";
    }
}
