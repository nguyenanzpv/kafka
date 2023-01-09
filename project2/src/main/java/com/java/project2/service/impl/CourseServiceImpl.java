package com.java.project2.service.impl;

import com.java.project2.dto.CourseDTO;
import com.java.project2.dto.PageDTO;
import com.java.project2.dto.StudentDTO;
import com.java.project2.entity.Course;
import com.java.project2.entity.Student;
import com.java.project2.repo.CourseRepo;
import com.java.project2.service.CourseService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.NoResultException;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {
    @Autowired
    CourseRepo courseRepo;
    @Autowired
    ModelMapper modelMapper;

    @Override
    @Transactional
    public void create(CourseDTO courseDTO) {
        Course course = new ModelMapper().map(courseDTO,Course.class);
        courseRepo.save(course);
    }

    @Override
    @Transactional
    public void update(CourseDTO courseDTO) {
        Course course = courseRepo.findById(courseDTO.getId()).orElseThrow(NoResultException::new);
        if(course != null){
            course.setName(courseDTO.getName());
            courseRepo.save(course);
        }
    }

    @Override
    @Transactional
    public void delete(int id) {
        courseRepo.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteAll(List<Integer> ids) {
        courseRepo.deleteAllById(ids);
    }

    @Override
    @Transactional
    public CourseDTO getById(int id) {
        Course course = courseRepo.findById(id).orElseThrow(NoResultException::new);
        CourseDTO courseDTO = new ModelMapper().map(course,CourseDTO.class);
        return courseDTO;
    }

    @Override
    @Transactional
    public PageDTO<CourseDTO> searchByName(String name, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Course> pageRS = courseRepo.searchByName(name,pageable);
        PageDTO<CourseDTO> pageDTO = new PageDTO<>();
        pageDTO.setTotalPages(pageRS.getTotalPages());
        pageDTO.setTotalElements(pageRS.getTotalElements());

        List<CourseDTO> courseDTOS = new ArrayList<>();
        for(Course course : pageRS.getContent()){
            courseDTOS.add(new ModelMapper().map(course,CourseDTO.class));
        }
        pageDTO.setContents(courseDTOS);
        return pageDTO;
    }

    @Override
    public List<CourseDTO> getAll() {
        List<CourseDTO> courseDTOS = new ArrayList<>();
        List<Course> courses = courseRepo.findAll();
        for(Course c:courses){
            CourseDTO courseDTO = new ModelMapper().map(c,CourseDTO.class);
            courseDTOS.add(courseDTO);
        }
        return courseDTOS;
    }
}
