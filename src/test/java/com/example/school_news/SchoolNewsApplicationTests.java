package com.example.school_news;

import com.example.school_news.pojo.New;
import com.example.school_news.service.NewService;
import com.example.school_news.util.Page4Navigator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

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

	
	@Test
	public void test1(){
		Page4Navigator<New> page= newService.listByUserWrite(0, 5, 5, 1);
		List<New> news=page.getContent();
		for (New n:news) {
			System.out.println("新闻id:"+n.getId()+"/n投稿人："+n.getUser_write().getName());
		}
	}

}

