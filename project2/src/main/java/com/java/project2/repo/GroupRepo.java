package com.java.project2.repo;

import com.java.project2.entity.Group;
import com.java.project2.entity.UserRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface GroupRepo extends JpaRepository<Group,Integer> {
    @Modifying
    @Query("select g from Group g where g.name = :name")
    public void deleteByName(@Param("name") String name);

    @Query("select g from Group g WHERE g.id = :gid")
    Page<Group> searchById(@Param("gid") int id, Pageable pageable);

    @Query("SELECT g FROM Group g JOIN g.users u WHERE u.name LIKE :name")
    Page<Group> searchByName(@Param("name") String name, Pageable pageable);

    @Query("select u from User u join u.groups g where u.id = :id")
    Page<Group> searchByUser(@Param("id") int id, Pageable pageable);

}
