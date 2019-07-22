package com.zhihu.service;

import com.zhihu.dao.QuestionDAO;
import com.zhihu.model.Question;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import java.util.List;

/**
 * Created by victorz on 2017/5/23.
 */
@Service
public class QuestionService {
    private static final Logger logger = LoggerFactory.getLogger(QuestionService.class);
    @Autowired
    QuestionDAO questionDao;

    @Autowired
    SensetiveService senService;

    public List getLatestQuestions(int userId, int offset, int limit) {
        return questionDao.selectLatestQuestions(userId, offset, limit);
    }

    //增加问题
    public int addQuestion(Question question) {
        //Html过滤
        question.setContent(HtmlUtils.htmlEscape(question.getContent()));
        //过滤敏感词
        question.setContent(senService.filter(question.getContent()));
        return questionDao.addQuestion(question) > 0 ? question.getId() : 0;
    }

    //获得数目
    public int getCommentCount(int questionid){
        return questionDao.getCommentCount(questionid);
    }

    //增加评论数目
    public void increaseCommentCount(int count,int questionid){
        questionDao.IncreaceCommentCount(count+1,questionid);
    }


    //返回question
    public Question getById(int id){
        return questionDao.select(id);
    }
}
