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

    /**
     * 1、审核id存在时，调用findByStateAndUserReadId
     * 查询某个人审核过的所有新闻
     * 2、查询id不存在时（即id=0），调用findByState
     * 查询所有已经审核过的新闻
     * @param start 分页参数
     * @param size  分页参数
     * @param navigatePages 导航最大页数
     * @param state  审核状态
     * @param user_read_id  新闻审核id
     * @return
     */
    public Page4Navigator<New> listByStateAndUserReadId(int start, int size, int navigatePages, String state, int user_read_id) {
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Pageable pageable = new PageRequest(start, size, sort);
        Page pageFromJPA;
        if (user_read_id == 0&&state.equals("未审核"))
            /*查询未审核的所有新闻*/
            pageFromJPA = newDAO.findByState(state, pageable);
        else if(user_read_id!= 0&&(state.equals("已审核")))
            /*查询审核人为某人的所有新闻*/
            pageFromJPA = newDAO.findByUserReadId(user_read_id, pageable);
        else if (user_read_id == 0&&state.equals("审核通过"))
            /*查询审核通过的所有新闻*/
            pageFromJPA = newDAO.findByState(state, pageable);

        else
            /*其他情况*/
            pageFromJPA=null;
        return new Page4Navigator<>(pageFromJPA, navigatePages);
    }

    /**
     * 模糊查询新闻信息，此时分审核过与未审核过的状态
     * 1、审核过  调用findByAllLikeByConfirmed的方法
     * 2、未审核  调用findByAllLikeByNoConfirmed的方法
     *
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
