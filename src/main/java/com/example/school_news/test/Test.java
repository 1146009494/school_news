package com.example.school_news.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Test {
    public static void main(String args[]) {
        //准备分类测试数据：

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try (
                Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3306/lvyue?useUnicode=true&characterEncoding=utf8",
                        "root", "admin");
                Statement s = c.createStatement();
        ) {
            for (int i = 1; i <= 5; i++) {
                String sqlFormat = "insert into User(name,password) values ('用户名%d', '密码%d')";
                String sql = String.format(sqlFormat, i,i);
                s.execute(sql);
            }

            System.out.println("已经成功创建5条分类测试数据");

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}
