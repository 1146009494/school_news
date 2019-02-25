package com.example.school_news.dao;

import com.example.school_news.pojo.New;
import com.example.school_news.pojo.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface NewDAO extends JpaRepository<New, Integer> {
    //    按审核状态查询结果
    Page<New> findByState(String state, Pageable pageable);


    /*模糊查询同时对审核人员进行模糊查询*/
    @Query("select n from New n where (" +
            "n.user_write.realname like ?1 or " +
//            "n.refer_time like ?1 or " +
            "n.note_title like ?1 or " +
            "n.note like ?1 or " +
            "n.note_type.name like ?1 or " +
            "n.user_read.realname like ?1  ) and (" +
            "n.state=?2)")
    Page<New> findByAllLikeByConfirmed(String keyword, String state, Pageable pageable);


    /*因为未审核过所以区别于上个查询，不能对审核人进行模糊查询*/
    @Query("select n from New n where (" +
            "n.user_write.realname like ?1 or " +
//            "n.refer_time like ?1 or " +
            "n.note_title like ?1 or " +
            "n.note like ?1 or " +
            "n.note_type.name like ?1 ) and (" +
            "n.state=?2)")
    Page<New> findByAllLikeByNoConfirmed(String keyword, String state, Pageable pageable);

    @Query("select n from New n where n.user_write.id=?1")
    Page<New> findByUserwrite(int user_write_id, Pageable pageable);


    @Query("select n from New n where (n.user_write.realname like ?1 or n.note_title like ?1 or " +
            "n.note like ?1 or n.note_type.name like ?1 ) and (" +
            "n.user_write.id=?2 )")
    Page<New> findByAllLikeAndUser(String keyword, int user_write_id, Pageable pageable);

    @Query("select n from New n where n.user_read.id=?1")
    Page<New> findByUserReadId(int user_read_id, Pageable pageable);
//    Page<New> findByUser_writeLikeAndAndNoteLike(User user,String note,Pageable pageable);
}
