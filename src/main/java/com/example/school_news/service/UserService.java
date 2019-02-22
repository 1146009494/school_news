package com.example.school_news.service;

import com.example.school_news.dao.UserDAO;
import com.example.school_news.pojo.User;
import com.example.school_news.util.Page4Navigator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    UserDAO userDAO;

    /*获取查询结果以及分页数据*/
    public Page4Navigator<User> list(int start, int size, int navigatePages) {
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Pageable pageable = new PageRequest(start, size, sort);
        Page pageFromJPA = userDAO.findAll(pageable);
        return new Page4Navigator<>(pageFromJPA, navigatePages);
    }

    /*按角色获取查询结果以及分页数据*/
    public Page4Navigator<User> listByRole(int start, int size, int navigatePages,String role) {
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Pageable pageable = new PageRequest(start, size, sort);
        Page pageFromJPA = userDAO.findByRole(role,pageable);
        return new Page4Navigator<>(pageFromJPA, navigatePages);
    }

    //    模糊查询获取全部数据 并分页显示
    public Page4Navigator<User> listByAllLike(int start, int size, int navigatePages, String keyword,String role) {
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Pageable pageable = new PageRequest(start, size, sort);
        Page pageFromJPA = userDAO.findByAllLike("%" + keyword + "%",role, pageable);

        return new Page4Navigator<>(pageFromJPA, navigatePages);
    }


    public void  add(User bean){
        userDAO.save(bean);
    }

    public void delete(int id){
        userDAO.deleteById(id);
    }

    /*跟据id查找数据*/
    public User get(int id){
        return userDAO.getOne(id);
    }

    /*跟据name查找数据*/
    public boolean get(String name){
        if(null!=userDAO.findByName(name))
            return true;
        else return false;

    }
    public void update(User bean){
        userDAO.save(bean);
    }


    public User checkLogin(User bean){
        return userDAO.findByNameAndPassword(bean.getName(),bean.getPassword());
    }
}
