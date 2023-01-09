package com.java.project2.repo;

import com.java.project2.entity.Student;
import com.java.project2.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StudentRepo extends JpaRepository<Student,Integer> {
    @Query("SELECT s FROM Student s WHERE s.studentCode = :x ")
    Page<Student> searchByStudentCode(@Param("x") String s, Pageable pageable);

    @Query("SELECT s FROM Student s JOIN s.user u WHERE u.name LIKE :x ")
    Page<Student> searchByName(@Param("x") String s, Pageable pageable);

    //@Query("SELECT s FROM Student s  WHERE s.studentCode = :code ")
    Optional<Student> findByStudentCode(String code);

    @Query("SELECT s FROM Student s")
    List<Student> getAll();

    @Modifying //danh dau cau lenh query la update/delete -> khi co join bang
    @Query("DELETE from Student s WHERE s.studentCode = :scode")
    public void deleteByCode(@Param("scode") String studentCode);
    @Query("SELECT s FROM Student s JOIN s.user u WHERE s.studentCode like :code AND u.name LIKE :name ")
    Page<Student> searchByNameAndCode(@Param("code") String code,@Param("name") String name, Pageable pageable);


}
