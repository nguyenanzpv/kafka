package com.java.project2.service.impl;

import com.java.project2.dto.PageDTO;
import com.java.project2.dto.StudentDTO;
import com.java.project2.dto.UserDTO;
import com.java.project2.entity.Student;
import com.java.project2.entity.User;
import com.java.project2.entity.UserRole;
import com.java.project2.repo.StudentRepo;
import com.java.project2.repo.UserRepo;
import com.java.project2.service.StudentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.NoResultException;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
@Service
public class StudentServiceImpl implements StudentService {
    // https://techmaster.vn/posts/36487/code-vi-du-moi-quan-he-one-to-one-voi-spring-boot
    @Autowired
    StudentRepo studentRepo;
    @Autowired
    UserRepo userRepo;

    @Autowired
    ModelMapper modelMapper;

    @Override
    @Transactional
    public void create(StudentDTO studentDTO) {
        User user = userRepo.findById(studentDTO.getId()).orElseThrow(NoResultException::new);
        for(UserRole userRole : user.getUserRoles()) {
            if (userRole.getRole().equals("ROLE_STUDENT")) {
                Student student = new Student();
                student.setStudentCode(studentDTO.getStudentCode());
                student.setId(studentDTO.getId());
                studentRepo.save(student);
            }
        }
    }

    @Override
    @Transactional
    public StudentDTO getById(int id) {
        Student s = studentRepo.findById(id).orElseThrow(NoResultException::new);
        /*StudentDTO studentDTO = new StudentDTO();
        studentDTO.setUserId(s.getUserId());
        studentDTO.setStudentCode(s.getStudentCode());
        //studentDTO.setUser(new ModelMapper().map(s.getUser(),UserDTO.class));*/
       return new ModelMapper().map(s,StudentDTO.class);
    }

    @Override
    @Transactional
    public void deleteByCode(String studentCode) {
        studentRepo.deleteByCode(studentCode);
    }

    @Override
    @Transactional
    public void deleteById(int userId) {
        Student student = studentRepo.findById(userId).orElseThrow(NoResultException::new); //java8 lambda
        studentRepo.deleteById(userId);
    }

    @Override
    @Transactional
    public void deleteAll(List<Integer> ids) {
        studentRepo.deleteAllById(ids);
    }

    @Override
    @Transactional
    public void update(StudentDTO studentDTO) {
        Student s = studentRepo.findById(studentDTO.getId()).orElseThrow(NoResultException::new);
        if(s != null){
            s.setStudentCode(studentDTO.getStudentCode());
            studentRepo.save(s);
        }
    }

    @Override
    public PageDTO<StudentDTO> search(String name,String studentCode, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Student> pageRS = null;

        //thu vien check null, empty... cua String

        if(StringUtils.hasText(name) && StringUtils.hasText(studentCode)){
            pageRS = studentRepo.searchByNameAndCode(name,studentCode,pageable);
        }else if(StringUtils.hasText(studentCode)){
            pageRS = studentRepo.searchByStudentCode(studentCode, pageable);
        }else if(StringUtils.hasText(name)){
            pageRS = studentRepo.searchByName(name,pageable);
        }
        else{
            pageRS = studentRepo.findAll(pageable);
        }

        PageDTO<StudentDTO> pageDTO = new PageDTO<>();
        pageDTO.setTotalPages(pageRS.getTotalPages());
        pageDTO.setTotalElements(pageRS.getTotalElements());

        List<StudentDTO> studentDTOS = new ArrayList<>();
        for(Student student:pageRS.getContent()){
            studentDTOS.add(new ModelMapper().map(student,StudentDTO.class));
        }
        pageDTO.setContents(studentDTOS); //set vao pageDto
        return pageDTO;
    }

    @Override
    public List<StudentDTO> getAll() {
        List<StudentDTO> studentDTOS = new ArrayList<>();
        List<Student> students = studentRepo.getAll();
        for(Student s:students){
            StudentDTO studentDTO = new ModelMapper().map(s,StudentDTO.class);
            studentDTOS.add(studentDTO);
        }
        return studentDTOS;
    }
}
