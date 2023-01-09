package com.java.project2.rest;

import com.java.project2.dto.*;
import com.java.project2.service.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/score")
public class ScoreRESTController {
    @Autowired
    ScoreService scoreService;

    @PostMapping("/new")
    //@ResponseStatus(value = HttpStatus.CREATED)
    public ResponseDTO<ScoreDTO> add(@ModelAttribute ScoreDTO scoreDTO){
        scoreService.create(scoreDTO);
        return ResponseDTO.<ScoreDTO>builder().status(200).data(scoreDTO).build();
    }

    @PostMapping("/new-json")
    //@ResponseStatus(value = HttpStatus.CREATED)
    public ResponseDTO<ScoreDTO> create(@RequestBody ScoreDTO scoreDTO){
        scoreService.create(scoreDTO);
        return ResponseDTO.<ScoreDTO>builder().status(200).data(scoreDTO).build();
    }

    @DeleteMapping("/delete") //delete?id=10
    public ResponseDTO<Void> delete(@RequestParam("id") int id ){
        scoreService.deleteById(id);
        return ResponseDTO.<Void>builder().status(200).build();
    }

    @GetMapping("/get/{id}") ///get/10
    public ResponseDTO<ScoreDTO> get(@PathVariable("id") int id){
        ScoreDTO scoreDTO = scoreService.getById(id);
        return ResponseDTO.<ScoreDTO>builder().status(200).data(scoreDTO).build();
        //jackson add on, gson
    }

    @PostMapping("/search")
    public ResponseDTO<PageDTO<ScoreDTO>> search(
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

        PageDTO<ScoreDTO> pageRS = null;
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
        return ResponseDTO.<PageDTO<ScoreDTO>>builder().status(200).data(pageRS).build();
    }

    @PostMapping("/edit")
    public ResponseDTO<ScoreDTO> edit(@ModelAttribute ScoreDTO scoreDTO) throws IllegalStateException, IOException {
        scoreService.update(scoreDTO);
        return ResponseDTO.<ScoreDTO>builder().status(200).data(scoreDTO)
                .build();
    }

    @PostMapping("/edit-json")
    //@ResponseStatus(value= HttpStatus.CREATED)
    public ResponseDTO<ScoreDTO> editJson(@RequestBody ScoreDTO scoreDTO){
        scoreService.update(scoreDTO);
        return ResponseDTO.<ScoreDTO>builder().status(200).data(scoreDTO)
                .build();
    }


}
