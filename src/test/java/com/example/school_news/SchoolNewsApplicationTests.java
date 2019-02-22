package com.example.school_news;

import com.example.school_news.pojo.New;
import com.example.school_news.service.NewService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SchoolNewsApplicationTests {
	@Autowired
	NewService newService;

	@Test
	public void contextLoads() {

	}

	@Test
	public void addSomeNew(){
		for (int i=0;i<10;i++){
			New bean=new New();
			bean.setNote("主标题"+i);
			bean.setState("审核通过");
			newService.add(bean);
		}
	}

}

