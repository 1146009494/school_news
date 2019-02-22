package com.example.school_news.service;

import com.example.school_news.dao.NewTypeDAO;
import com.example.school_news.pojo.NewType;
import com.example.school_news.pojo.NewType;
import com.example.school_news.util.Page4Navigator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class NewTypeService {
    @Autowired
    NewTypeDAO newTypeDAO;


    /*获取查询结果以及分页数据*/
    public Page4Navigator<NewType> list(int start, int size, int navigatePages) {
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Pageable pageable = new PageRequest(start, size, sort);
        Page pageFromJPA = newTypeDAO.findAll(pageable);
        return new Page4Navigator<>(pageFromJPA, navigatePages);
    }

    public void  add(NewType bean){
        newTypeDAO.save(bean);
    }

    public void delete(int id){
        newTypeDAO.deleteById(id);
    }

    public NewType get(int id){
        return newTypeDAO.getOne(id);
    }

    public void update(NewType bean){
        newTypeDAO.save(bean);
    }

}
