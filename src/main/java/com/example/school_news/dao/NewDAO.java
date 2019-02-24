package com.example.school_news.dao;

import com.example.school_news.pojo.New;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface NewDAO extends JpaRepository<New,Integer> {
    //    按审核状态查询结果
    Page<New> findByState(String state, Pageable pageable);

    @Query("select n from New  n where (" +
            "n.user_write.name like ?1 or " +
//            "n.refer_time like ?1 or " +
            "n.note_title like ?1 or " +
            "n.note like ?1 or " +
            "n.note_type.name like ?1 or " +
            "n.user_write.name like ?1 ) and (" +
            "n.state=?2)")
    Page<New> findByAllLike(String keyword,String state, Pageable pageable);

//    Page<New> findByUserwrite(int user_write, Pageable pageable);





}
