package com.java.project2.service.impl;

import com.java.project2.dto.*;
import com.java.project2.entity.Course;
import com.java.project2.entity.Score;
import com.java.project2.entity.Student;
import com.java.project2.repo.CourseRepo;
import com.java.project2.repo.ScoreRepo;
import com.java.project2.repo.StudentRepo;
import com.java.project2.service.ScoreService;
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
public class ScoreServiceImpl implements ScoreService {
    @Autowired
    ScoreRepo scoreRepo;
    @Autowired
    CourseRepo courseRepo;
    @Autowired
    StudentRepo studentRepo;
    @Autowired
    ModelMapper modelMapper;

    @Override
    @Transactional
    public void create(ScoreDTO scoreDTO) {
        Score score = new Score();
        score.setScore(scoreDTO.getScore());
        CourseDTO courseDTO = scoreDTO.getCourse();
        Course course = courseRepo.findById(courseDTO.getId()).orElseThrow(NoResultException::new);
        score.setCourse(course);
        StudentDTO studentDTO = scoreDTO.getStudent();
        Student student = studentRepo.findById(studentDTO.getId()).orElseThrow(NoResultException::new);
        score.setStudent(student);
        scoreRepo.save(score);
    }

    @Override
    @Transactional
    public ScoreDTO getById(int id) {
        Score score = scoreRepo.findById(id).orElseThrow(NoResultException::new);
        return new ModelMapper().map(score,ScoreDTO.class);
    }

    @Override
    @Transactional
    public void deleteById(int id) {
        Score score = scoreRepo.findById(id).orElseThrow(NoResultException::new);
        if(score!=null){
            scoreRepo.deleteById(id);
        }
    }

    @Override
    @Transactional
    public void deleteAll(List<Integer> ids) {
        scoreRepo.deleteAllById(ids);
    }

    @Override
    @Transactional
    public void update(ScoreDTO scoreDTO) {
        Score score = scoreRepo.findById(scoreDTO.getId()).orElseThrow(NoResultException::new);
        if(score!=null){
            score.setScore(scoreDTO.getScore());
            scoreRepo.save(score);
        }
    }

    @Override
    @Transactional
    public PageDTO<ScoreDTO> searchById(Integer id, int page, int size) {
        Pageable pageable = PageRequest.of(page,size);
        Page<Score> pageRS = scoreRepo.searchById(id, pageable);
        PageDTO<ScoreDTO> pageDTO = new PageDTO<>();
        pageDTO.setTotalPages(pageRS.getTotalPages());
        pageDTO.setTotalElements(pageRS.getTotalElements());

        List<ScoreDTO> scoreDTOS = new ArrayList<>();
        for(Score s:pageRS.getContent()){
            scoreDTOS.add(new ModelMapper().map(s,ScoreDTO.class));
        }
        pageDTO.setContents(scoreDTOS); //set vao pageDto
        return pageDTO;
    }

    @Override
    public PageDTO<ScoreDTO> searchByCourseId(Integer id, int page, int size) {
        Pageable pageable = PageRequest.of(page,size);
        Page<Score> pageRS = scoreRepo.searchByCourseId(id, pageable);
        PageDTO<ScoreDTO> pageDTO = new PageDTO<>();
        pageDTO.setTotalPages(pageRS.getTotalPages());
        pageDTO.setTotalElements(pageRS.getTotalElements());

        List<ScoreDTO> scoreDTOS = new ArrayList<>();
        for(Score s:pageRS.getContent()){
            scoreDTOS.add(new ModelMapper().map(s,ScoreDTO.class));
        }
        pageDTO.setContents(scoreDTOS); //set vao pageDto
        return pageDTO;
    }

    @Override
    public PageDTO<ScoreDTO> searchByStudentId(Integer id, int page, int size) {
        Pageable pageable = PageRequest.of(page,size);
        Page<Score> pageRS = scoreRepo.searchByStudentId(id, pageable);
        PageDTO<ScoreDTO> pageDTO = new PageDTO<>();
        pageDTO.setTotalPages(pageRS.getTotalPages());
        pageDTO.setTotalElements(pageRS.getTotalElements());

        List<ScoreDTO> scoreDTOS = new ArrayList<>();
        for(Score s:pageRS.getContent()){
            scoreDTOS.add(new ModelMapper().map(s,ScoreDTO.class));
        }
        pageDTO.setContents(scoreDTOS); //set vao pageDto
        return pageDTO;
    }

    @Override
    @Transactional
    public PageDTO<ScoreDTO> searchByScore(Double score, int page, int size) {
        Pageable pageable = PageRequest.of(page,size);
        Page<Score> pageRS = scoreRepo.searchByScore(score, pageable);
        PageDTO<ScoreDTO> pageDTO = new PageDTO<>();
        pageDTO.setTotalPages(pageRS.getTotalPages());
        pageDTO.setTotalElements(pageRS.getTotalElements());

        List<ScoreDTO> scoreDTOS = new ArrayList<>();
        for(Score s:pageRS.getContent()){
            scoreDTOS.add(new ModelMapper().map(s,ScoreDTO.class));
        }
        pageDTO.setContents(scoreDTOS); //set vao pageDto
        return pageDTO;
    }

    @Override
    public PageDTO<ScoreDTO> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page,size);
        Page<Score> pageRS = scoreRepo.findAll(pageable);

        PageDTO<ScoreDTO> pageDTO = new PageDTO<>();
        pageDTO.setTotalPages(pageRS.getTotalPages());
        pageDTO.setTotalElements(pageRS.getTotalElements());

        List<ScoreDTO> scoreDTOS = new ArrayList<>();
        for(Score s:pageRS.getContent()){
            scoreDTOS.add(new ModelMapper().map(s,ScoreDTO.class));
        }
        pageDTO.setContents(scoreDTOS); //set vao pageDto
        return pageDTO;
    }
}
