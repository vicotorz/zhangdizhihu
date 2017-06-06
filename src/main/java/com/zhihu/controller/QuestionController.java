package com.zhihu.controller;

import com.zhihu.Utils.JsonUtil;
import com.zhihu.model.HostHolder;
import com.zhihu.model.Question;
import com.zhihu.model.ViewObject;
import com.zhihu.service.QuestionService;
import com.zhihu.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by dell on 2017/5/23.
 */
@Controller
public class QuestionController {
    private static final Logger logger = LoggerFactory.getLogger(QuestionController.class);

    @Autowired
    QuestionService questionservice;

    @Autowired
    HostHolder hostholeder;//上下文存入用户

    @RequestMapping(value = "/question/add", method = {RequestMethod.POST})
    @ResponseBody
    public String addQuestion(@RequestParam("title") String title, @RequestParam("content") String content) {
        //返回json
        try {
            //(#{title},#{content},#{userId},#{createdDate},#{commentCount})
            Question question = new Question();
            question.setContent(content);
            question.setTitle(title);
            question.setCreatedDate(new Date());
            question.setCommentCount(0);

            if (hostholeder.getUser() == null) {
                //匿名用过户
                question.setUserId(JsonUtil.DEFAULT_USER_ID);
            } else {
                question.setUserId(hostholeder.getUser().getId());
            }

            if (questionservice.addQuestion(question) > 0) {
                //成功
                return JsonUtil.getJSONString(0);
            }
        } catch (Exception e) {
            logger.error("增加题目失败" + e.getMessage());
        }
        return JsonUtil.getJSONString(1, "增加失败");
    }
}
