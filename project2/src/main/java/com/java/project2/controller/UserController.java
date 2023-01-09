package com.java.project2.controller;

import com.java.project2.dto.PageDTO;
import com.java.project2.dto.UserDTO;
import com.java.project2.entity.User;
import com.java.project2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Date;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {
    // https://javatute.com/jpa/spring-data-jpa-crudrepository-delete-and-deleteall/
    @Autowired
    UserService userService;

    @GetMapping("/new")
    public String add(){
        return "user/add.html";
    }

    @PostMapping("/new")
    public String add(@ModelAttribute UserDTO u) throws IllegalStateException, IOException {
        if(u.getFile() != null && !u.getFile().isEmpty()){
            final String UPLOAD_FOLDER = "C:/nntan/Project/ASPNETCORE/Jmaster/Java-SpringBoot-BaiTapSection/project2/src/main/resources/static/upload/";
            String filename = u.getFile().getOriginalFilename();
            File newFile = new File(UPLOAD_FOLDER + filename);
            u.getFile().transferTo(newFile);
            u.setAvatar(filename);//save to db
        }
        userService.create(u);
        return "redirect:/user/search";
    }

    @PostMapping("/upload-multi")
    public String add(@RequestParam("files") MultipartFile[] files) throws IllegalStateException, IOException {
        System.out.println(files.length);
        for (MultipartFile file : files)
            if (!file.isEmpty()) {
                final String UPLOAD_FOLDER = "C:/nntan/Project/ASPNETCORE/Jmaster/Java-SpringBoot-BaiTapSection/project2/src/main/resources/static/upload/";

                String filename = file.getOriginalFilename();
                File newFile = new File(UPLOAD_FOLDER + filename);

                file.transferTo(newFile);
            }

        return "redirect:/user/search";
    }

    // /user/download?filename=abc.jpg
    //dung cho dung luong nho
    @GetMapping("/download")
    public void download(@RequestParam("filename") String filename, HttpServletResponse response) throws IOException
    {
        final String UPLOAD_FOLDER="C:/nntan/Project/ASPNETCORE/Jmaster/Java-SpringBoot-BaiTapSection/project2/src/main/resources/static/upload/";
        File file = new File(UPLOAD_FOLDER+filename);
        //co the chi dinh dinh dang file la gi qua content type
        //java.nio.file.Files
        Files.copy(file.toPath(),response.getOutputStream());
    }

    @GetMapping("/search")
    public String search(
            @RequestParam(name="id", required = false) Integer id,
            @RequestParam(name ="name", required=false) String name,
            @RequestParam(name = "start", required = false) @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm") Date start,
            @RequestParam(name = "end", required = false) @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm") Date end,
            @RequestParam(name = "size", required = false) Integer size,
            @RequestParam(name = "page", required = false) Integer page,
            Model model
    )
    {
        size = size == null ? 10 : size;
        page = page == null ? 0 : page;
        name = name == null ?"" : name;

        PageDTO<UserDTO> pageRS = userService.searchByName("%"+name+"%",page,size);
        model.addAttribute("totalPage",pageRS.getTotalPages());
        model.addAttribute("count", pageRS.getTotalElements());
        model.addAttribute("userList", pageRS.getContents());

        // luu lai du lieu set sang view lai
        model.addAttribute("id", id);
        model.addAttribute("name", name);
        model.addAttribute("start", start);
        model.addAttribute("end", end);

        model.addAttribute("page", page);
        model.addAttribute("size", size);

        return "user/search.html";
    }

    @GetMapping("/edit") // ?id=1
    public String edit(@RequestParam("id") int id, Model model) {
        model.addAttribute("user", userService.getById(id));
        return "user/edit.html";
    }

    @PostMapping("/edit")
	public String edit(@ModelAttribute UserDTO userDTO) throws IllegalStateException, IOException {
        if(userDTO.getFile() != null && !userDTO.getFile().isEmpty()){
            final String UPLOAD_FOLDER = "C:/nntan/Project/ASPNETCORE/Jmaster/Java-SpringBoot-BaiTapSection/project2/src/main/resources/static/upload/";
            String filename = userDTO.getFile().getOriginalFilename();
            File newFile = new File(UPLOAD_FOLDER + filename);
            userDTO.getFile().transferTo(newFile);
            userDTO.setAvatar(filename);//save to db
        }
        userService.update(userDTO);
		return "redirect:/user/search";
	}

    @GetMapping("/edit-password")
    public String editPassword(@RequestParam("id") int id, Model model) {
        model.addAttribute("user", userService.getById(id));
        return "user/edit-password.html";
    }

    @PostMapping("/edit-password")
    public String editPassword(@ModelAttribute UserDTO userDTO) throws IllegalStateException, IOException {
        if(userDTO.getPassword() != null && !userDTO.getPassword().isEmpty()){
            userService.updatePassword(userDTO);
        }
        return "redirect:/user/search";
    }

    //@DeleteMapping("/delete") //?id=
    @GetMapping("/delete")
    public String delete(@RequestParam("id") int id) {
        userService.deleteById(id);
        return "redirect:/user/search";
    }

    //@DeleteMapping("/delete-all") //?ids=1,2,3
    @GetMapping("/delete-all")
    public String deleteAll(@RequestParam(value = "cid[]") List<Integer> ids) {
        userService.deleteAll(ids);
        return "redirect:/user/search";
    }

    @GetMapping("/get/{id}")//get/10
    public String get(@PathVariable("id") int id, Model model) {
        model.addAttribute("user", userService.getById(id));
        return "user/view.html";
    }

}
