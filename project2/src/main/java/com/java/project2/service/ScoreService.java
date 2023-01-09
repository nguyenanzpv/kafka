package com.java.project2.service;

import com.java.project2.dto.PageDTO;
import com.java.project2.dto.ScoreDTO;
import com.java.project2.dto.StudentDTO;

import java.util.List;

public interface ScoreService {
    public void create(ScoreDTO scoreDTO);
    public ScoreDTO getById(int id);
    public void deleteById(int id);
    public void deleteAll(List<Integer> ids);
    public void update(ScoreDTO scoreDTO);
    public PageDTO<ScoreDTO> searchById(Integer id, int page, int size);
    public PageDTO<ScoreDTO> searchByCourseId(Integer id, int page, int size);
    public PageDTO<ScoreDTO> searchByStudentId(Integer id, int page, int size);
    public PageDTO<ScoreDTO> searchByScore(Double score, int page, int size);
    public PageDTO<ScoreDTO> getAll(int page, int size);
}
