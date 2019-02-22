package com.example.school_news.service;

import com.example.school_news.dao.NewDAO;
import com.example.school_news.pojo.New;
import com.example.school_news.pojo.User;
import com.example.school_news.util.Page4Navigator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class NewService {
    @Autowired
    NewDAO newDAO;


    /*获取查询结果以及分页数据*/
    public Page4Navigator<New> list(int start, int size, int navigatePages) {
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Pageable pageable = new PageRequest(start, size, sort);
        Page pageFromJPA = newDAO.findAll(pageable);
        return new Page4Navigator<>(pageFromJPA, navigatePages);
    }
//    /*获取查询结果以及分页数据*/
//    public Page4Navigator<New> listByUserWrite(int start, int size, int navigatePages,User user_write) {
//        Sort sort = new Sort(Sort.Direction.DESC, "id");
//        Pageable pageable = new PageRequest(start, size, sort);
//        Page pageFromJPA = newDAO.findByUserwrite(user_write,pageable);
//        return new Page4Navigator<>(pageFromJPA, navigatePages);
//    }



    /*获取查询结果以及分页数据*/
    public Page4Navigator<New> listByState(int start, int size, int navigatePages,String state) {
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Pageable pageable = new PageRequest(start, size, sort);
        Page pageFromJPA = newDAO.findByState(state,pageable);
        return new Page4Navigator<>(pageFromJPA, navigatePages);
    }

    //    按状态模糊查询获取全部数据 并分页显示
    public Page4Navigator<New> listByAllLikeAndState(int start, int size, int navigatePages, String keyword,String state) {
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Pageable pageable = new PageRequest(start, size, sort);
        Page pageFromJPA = newDAO.findByAllLike("%" + keyword + "%",state, pageable);

        return new Page4Navigator<>(pageFromJPA, navigatePages);
    }

    public void  add(New bean){
        newDAO.save(bean);
    }

    public New  addAndGet(New bean){
       return newDAO.saveAndFlush(bean);
    }

    public void delete(int id){
        newDAO.deleteById(id);
    }

    public New get(int id){
        return newDAO.getOne(id);
    }

    public void update(New bean){
        newDAO.save(bean);
    }
}
