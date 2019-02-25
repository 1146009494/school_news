package com.example.school_news.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class MyWebMvc extends WebMvcConfigurerAdapter {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
//        super.addViewControllers(registry);
//        前台
        registry.addViewController("/fore_home").setViewName("fore/home");
        registry.addViewController("/fore_login").setViewName("fore/login");
        registry.addViewController("/fore_register").setViewName("fore/register");

        /*个人中心*/
//        registry.addViewController("/personal_zone").setViewName("fore/personal_zone");

        registry.addViewController("/").setViewName("redirect:fore_home");
        registry.addViewController("/home").setViewName("admin/home");
        registry.addViewController("/login").setViewName("admin/login");
        /*个人信息管理模块*/
        registry.addViewController("/user_info").setViewName("admin/user_info");
        /*个人新闻管理模块*/
        registry.addViewController("/mynew").setViewName("admin/mynew");

        /*新闻审核员模块*/
        registry.addViewController("/myconfirm").setViewName("admin/myconfirm");

        /*用户管理模块*/
        registry.addViewController("/user_add").setViewName("admin/user_add");
        registry.addViewController("/user_write").setViewName("admin/user_write");
        registry.addViewController("/user_read").setViewName("admin/user_read");
        /*新闻管理模块*/
        registry.addViewController("/new_type").setViewName("admin/new_type");
        registry.addViewController("/new_add").setViewName("admin/new_add");
        registry.addViewController("/new_confirm").setViewName("admin/new_confirm");
        registry.addViewController("/new_NoConfirm").setViewName("admin/new_NoConfirm");

    }
}
