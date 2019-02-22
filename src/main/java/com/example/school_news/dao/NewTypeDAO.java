package com.example.school_news.dao;

import com.example.school_news.pojo.NewType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewTypeDAO extends JpaRepository<NewType,Integer> {
}
