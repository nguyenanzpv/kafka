package com.java.project2.rest;

import com.java.project2.dto.*;
import com.java.project2.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequestMapping("/api/group")
public class GroupRESTController {
    @Autowired
    GroupService groupService;

    @PostMapping("/new")
    //@ResponseStatus(value = HttpStatus.CREATED)
    public ResponseDTO<GroupDTO> add(@ModelAttribute  @Valid GroupDTO groupDTO){
        groupService.create(groupDTO);
        return ResponseDTO.<GroupDTO>builder().status(200).data(groupDTO).build();
    }

    @PostMapping("/new-json")
    //@ResponseStatus(value = HttpStatus.CREATED)
    public ResponseDTO<GroupDTO> create(@RequestBody GroupDTO groupDTO){
        groupService.create(groupDTO);
        return ResponseDTO.<GroupDTO>builder().status(200).data(groupDTO).build();
    }

    @DeleteMapping("/delete") //delete?id=10
    public ResponseDTO<Void> delete(@RequestParam("id") int id ){
        groupService.delete(id);
        return ResponseDTO.<Void>builder().status(200).build();
    }

    @GetMapping("/get/{id}") ///get/10
    public ResponseDTO<GroupDTO> get(@PathVariable("id") int id){
        GroupDTO groupDTO = groupService.getById(id);
        return ResponseDTO.<GroupDTO>builder().status(200).data(groupDTO).build();
        //jackson add on, gson
    }

    @PostMapping("/search")
    public ResponseDTO<PageDTO<GroupDTO>> search(
            @RequestParam(name ="id", required=false) Integer id,
            @RequestParam(name ="name", required=false) String name,
            @RequestParam(name = "userId", required = false) Integer userId,
            @RequestParam(name = "studentId", required = false) Integer studentId,
            @RequestParam(name = "size", required = false) Integer size,
            @RequestParam(name = "page", required = false) Integer page,
            Model model
    )
    {
        size = size == null ? 10 : size;
        page = page == null ? 0 : page;

        PageDTO<GroupDTO> pageRS = null;

        if(id == null){
            pageRS = groupService.searchByName("%"+name+"%",page,size);

        }
        else if(name == "" || name.isEmpty()){
            pageRS = groupService.searchById(id,page,size);

        } else if(name != "" && id != null && userId != null){
            pageRS = groupService.searchByUser(userId,page,size);

        }
        return ResponseDTO.<PageDTO<GroupDTO>>builder().status(200).data(pageRS).build();
    }

    @PostMapping("/edit")
    public ResponseDTO<GroupDTO> edit(@ModelAttribute GroupDTO groupDTO) throws IllegalStateException, IOException {
        groupService.update(groupDTO);
        return ResponseDTO.<GroupDTO>builder().status(200).data(groupDTO)
                .build();
    }

    @PostMapping("/edit-json")
    //@ResponseStatus(value= HttpStatus.CREATED)
    public ResponseDTO<GroupDTO> editJson(@RequestBody GroupDTO groupDTO){
        groupService.update(groupDTO);
        return ResponseDTO.<GroupDTO>builder().status(200).data(groupDTO).build();
    }
}
