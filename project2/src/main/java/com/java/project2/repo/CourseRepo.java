package com.java.project2.repo;

import com.java.project2.entity.Course;
import com.java.project2.entity.UserRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CourseRepo extends JpaRepository<Course,Integer> {
    @Query("select c from Course c WHERE c.name like :name")
    Page<Course> searchByName(@Param("name") String name, Pageable pageable);
}
