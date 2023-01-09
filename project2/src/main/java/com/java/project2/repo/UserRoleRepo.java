package com.java.project2.repo;

import com.java.project2.entity.UserRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRoleRepo extends JpaRepository<UserRole,Integer> {
    @Modifying //danh dau cau lenh query la update/delete -> khi co join bang
    @Query("DELETE from UserRole ur WHERE ur.user.id = :uid")
    public void deleteByUserId(@Param("uid") int userId);

    //@Modifying //danh dau cau lenh query la update/delete -> khi co join bang
    @Query("select ur from UserRole ur JOIN ur.user u WHERE u.id = :uid")
    Page<UserRole> searchByUserId(@Param("uid") int userId, Pageable pageable);

    //@Modifying //danh dau cau lenh query la update/delete -> khi co join bang
    @Query("select ur from UserRole ur JOIN ur.user u WHERE u.id = :uid and ur.role = :role")
    Page<UserRole> searchByUserIdRole(@Param("uid") int userId,@Param("role") String role, Pageable pageable);

    //@Modifying //danh dau cau lenh query la update/delete
    @Query("select ur from UserRole ur WHERE ur.role like :role")
    Page<UserRole> searchByRole(@Param("role") String role, Pageable pageable);

    void deleteByRole(String role);// xoa theo cot role
}
