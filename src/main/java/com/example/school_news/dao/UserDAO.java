package com.example.school_news.dao;

import com.example.school_news.pojo.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserDAO extends JpaRepository<User,Integer> {
    //    模糊查询多字段
        @Query("select u from User  u where (" +
            "u.name like ?1 or " +
            "u.password like ?1 or " +
            "u.realname like ?1 or " +
            "u.phone like ?1) and (" +
            "u.role=?2)")
    Page<User> findByAllLike(String keyword,String role, Pageable pageable);

//    按角色查询结果
    Page<User> findByRole(String role,Pageable pageable);

    User findByNameAndPassword(String name,String password);

    User findByName(String name);
}
