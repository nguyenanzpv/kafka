package com.java.project2.rest;

import com.java.project2.dto.PageDTO;
import com.java.project2.dto.ResponseDTO;
import com.java.project2.dto.UserDTO;
import com.java.project2.dto.UserRoleDTO;
import com.java.project2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;

@RestController
@RequestMapping("/api/user")
public class UserRESTController {
    //https://viblo.asia/p/post-request-su-dung-postman-L4x5xw41lBM
    //https://github.com/springdoc/springdoc-openapi/issues/1416
    //https://swagger.io/docs/specification/describing-request-body/multipart-requests/
    //https://swagger.io/docs/specification/describing-request-body/multipart-requests/
    //https://swagger.io/docs/specification/describing-request-body/file-upload/
    //https://www.baeldung.com/postman-upload-file-json
    //https://www.bezkoder.com/spring-boot-file-upload/
    //https://www.javaguides.net/2018/11/spring-boot-2-file-upload-and-download-rest-api-tutorial.html
    //https://shareprogramming.net/jackson-json-de-quy-vo-tan-trong-moi-quan-he-2-chieu/#:~:text=%40JsonManagedReference%20%C4%91%C6%B0%E1%BB%A3c%20xem%20nh%C6%B0%20l%C3%A0,s%E1%BA%BD%20%C4%91%C6%B0%E1%BB%A3c%20serialized%20b%C3%ACnh%20th%C6%B0%E1%BB%9Dng.
    @Autowired
    UserService userService;

    public void add(@RequestParam("files") MultipartFile[] file) throws IOException {

    }


    @PostMapping("/new")
    //@ResponseStatus(value = HttpStatus.CREATED)
    public ResponseDTO<Void> add(@ModelAttribute UserDTO userDTO) throws IOException {
        if(userDTO.getFile() != null && !userDTO.getFile().isEmpty()){
            final String UPLOAD_FOLDER = "C:/nntan/Project/ASPNETCORE/Jmaster/Java-SpringBoot-BaiTapSection/project2/src/main/resources/static/upload/";
            String filename = userDTO.getFile().getOriginalFilename();
            File newFile = new File(UPLOAD_FOLDER + filename);
            userDTO.getFile().transferTo(newFile);
            userDTO.setAvatar(filename);//save to db
        }
        userService.create(userDTO);
        return ResponseDTO.<Void>builder().status(200).build();
    }

    /*@PostMapping("/new-json")
    @ResponseStatus(value = HttpStatus.CREATED)
    public  void create(@RequestBody UserDTO userDTO) throws IOException {
        if(userDTO.getFile() != null && !userDTO.getFile().isEmpty()){
            final String UPLOAD_FOLDER = "C:/nntan/Project/ASPNETCORE/Jmaster/Java-SpringBoot-BaiTapSection/project2/src/main/resources/static/upload/";
            String filename = userDTO.getFile().getOriginalFilename();
            File newFile = new File(UPLOAD_FOLDER + filename);
            userDTO.getFile().transferTo(newFile);
            userDTO.setAvatar(filename);//save to db
        }
        userService.create(userDTO);
    }*/

    @DeleteMapping("/delete") //delete?id=10
    public ResponseDTO<Void> delete(@RequestParam("id") int id ){
        userService.deleteById(id);
        return ResponseDTO.<Void>builder().status(200)
                .build();
    }

    @GetMapping("/get/{id}") ///get/10
    public ResponseDTO<UserDTO> get(@PathVariable("id") int id){
        UserDTO userDTO = userService.getById(id);
        return ResponseDTO.<UserDTO>builder().status(200)
                .data(userDTO).build();
        //jackson add on, gson
    }

    @GetMapping("/search")
    public  ResponseDTO<PageDTO<UserDTO>> search(
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

        PageDTO<UserDTO> pageRS = null;
        pageRS = userService.searchByName(name,page,size);
        return ResponseDTO.<PageDTO<UserDTO>>builder().status(200)
                .data(pageRS).build();
    }
    @PostMapping("/edit")
    public ResponseDTO<Void> edit(@ModelAttribute UserDTO userDTO) throws IllegalStateException, IOException {
        if(userDTO.getFile() != null && !userDTO.getFile().isEmpty()){
            final String UPLOAD_FOLDER = "E:/JMaster/JavaSpringBoot/project2/project2/src/main/resources/static/upload/";
            String filename = userDTO.getFile().getOriginalFilename();
            File newFile = new File(UPLOAD_FOLDER + filename);
            userDTO.getFile().transferTo(newFile);
            userDTO.setAvatar(filename);//save to db
        }
        userService.update(userDTO);
        return ResponseDTO.<Void>builder().status(200)
                .build();
    }


}
