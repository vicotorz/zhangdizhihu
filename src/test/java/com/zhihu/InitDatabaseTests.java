package com.zhihu;

import com.zhihu.dao.QuestionDAO;
import com.zhihu.dao.UserDAO;
import com.zhihu.model.Question;
import com.zhihu.model.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.Random;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ZhangdizhihuApplication.class)
@Sql("/init-schema.sql")
public class InitDatabaseTests {
    @Autowired
    UserDAO userDAO;
    @Autowired
    QuestionDAO questionDAO;
    @Test
    public void contextLoads() {
        Random random = new Random();
        for (int i = 0; i < 11; ++i) {
            User user = new User();
            user.setHeadUrl(null);
            user.setName(String.format("USER%d", i+1));
            user.setPassword("");
            user.setSalt("");
            userDAO.addUser(user);

            user.setPassword("xx");
            userDAO.updatePassword(user);


            Question question=new Question();
            question.setTitle(String.format("TITLE{%d}",i+1));
            question.setContent("bababa"+i);
            Date date=new Date();
            date.setTime(date.getTime()+1000*3600*i);
            question.setCreatedDate(date);
            question.setUserId(i+1);
            question.setCommentCount(i);
            questionDAO.addQuestion(question);
        }
        //更新

        Assert.assertEquals("xx",userDAO.select(1).getPassword());
        //删除
        //userDAO.deleteById(1);
        //Assert.assertNull(userDAO.select(1));

        System.out.println(questionDAO.selectLatestQuestions(0,0,10));


    }
}
