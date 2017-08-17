package com.newcoder.duihua;

import com.newcoder.duihua.dao.QuestionDao;
import com.newcoder.duihua.dao.UserDao;
import com.newcoder.duihua.model.Question;
import com.newcoder.duihua.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.Random;

@RunWith(SpringRunner.class)
@SpringBootTest

public class InitDataTests {
	@Autowired
	UserDao userDao;
	@Autowired
	QuestionDao questionDao;
	@Test
	public void initData() {
		Random random = new Random();
		for(int i=0;i<10;i++) {
			User user = new User();
			user.setHeadUrl("http://images.nowcoder.com/head/"+random.nextInt(1000)+".png");
			user.setName("USER"+i);
			user.setPassword("");
			user.setSalt("");
			userDao.addUser(user);
		}

	}

	@Test
	public  void delectSelectUodate() {
		userDao.deleteById(1);
		User u = userDao.selectById(2);
		System.out.println(u);
	}

	@Test
	public void insertQuestion() {
		for (int i = 0; i <10 ; i++) {
		Question question = new Question();
			question.setCommentCount(i);
			Date date = new Date();
			date.setTime(date.getTime()+1000*3600*i);
			question.setCreateDate(date);
			question.setUserId(i+1);
			question.setTitle("TITLE"+i);
			question.setContent("balabalabalaal"+i);
			questionDao.addQuestion(question);
		}

	}

	@Test
	public void selecetQues() {
		System.out.println(questionDao.selectLatestQuestions(0,0,10));
	}

}

	/*@Test
	public void uuidTest() {
		System.out.println(UUID.randomUUID().toString());
	}*/
