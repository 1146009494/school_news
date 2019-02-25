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

    /*获取查询结果以及分页数据*/
    public Page4Navigator<New> listByUserWrite(int start, int size, int navigatePages, int user_write_id) {
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Pageable pageable = new PageRequest(start, size, sort);
        Page pageFromJPA = newDAO.findByUserwrite(user_write_id, pageable);
        return new Page4Navigator<>(pageFromJPA, navigatePages);
    }


    /*获取查询结果以及分页数据*/
    public Page4Navigator<New> listByState(int start, int size, int navigatePages, String state) {
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Pageable pageable = new PageRequest(start, size, sort);
        Page pageFromJPA = newDAO.findByState(state, pageable);
        return new Page4Navigator<>(pageFromJPA, navigatePages);
    }

    /**
     * 模糊查询新闻信息，此时分审核过与未审核过的状态
     * 1、审核过  调用byconfirmed的方法
     * 2、未审核  调用bynoconfirmed的方法
     * @param start
     * @param size
     * @param navigatePages
     * @param keyword
     * @param state
     * @return
     */
    public Page4Navigator<New> listByAllLikeAndState(int start, int size, int navigatePages, String keyword, String state) {
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Pageable pageable = new PageRequest(start, size, sort);
        Page pageFromJPA;
        if (state.equals("未审核"))
            pageFromJPA = newDAO.findByAllLikeByNoConfirmed("%" + keyword + "%", state, pageable);
        else
            pageFromJPA = newDAO.findByAllLikeByConfirmed("%" + keyword + "%", state, pageable);
        return new Page4Navigator<>(pageFromJPA, navigatePages);
    }

    public void add(New bean) {
        newDAO.save(bean);
    }

    /**
     * 增加一个新闻并返回带id的本条新闻信息
     *
     * @param bean 新闻信息bean
     * @return 带id的本条新闻信息
     */
    public New addAndGet(New bean) {
        return newDAO.saveAndFlush(bean);
    }

    public void delete(int id) {
        newDAO.deleteById(id);
    }

    public New get(int id) {
        return newDAO.getOne(id);
    }

    public void update(New bean) {
        newDAO.save(bean);
    }

    public Page4Navigator<New> listByAllLikeAndUser(int start, int size, int navigatePages, String keyword, int user_write_id) {
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Pageable pageable = new PageRequest(start, size, sort);
        Page pageFromJPA = newDAO.findByAllLikeAndUser("%" + keyword + "%", user_write_id, pageable);

        return new Page4Navigator<>(pageFromJPA, navigatePages);
    }
}
