package com.zhihu.controller;

import com.zhihu.Utils.JsonUtil;
import com.zhihu.model.*;
import com.zhihu.service.CommentService;
import com.zhihu.service.QuestionService;
import com.zhihu.service.SensetiveService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.HtmlUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.zhihu.Utils.JsonUtil.DEFAULT_USER_ID;

/**
 * Created by dell on 2017/6/10.
 */
@Controller
public class CommentController {
    private static final Logger logger = LoggerFactory.getLogger(CommentController.class);

    @Autowired
    CommentService commentService;

    @Autowired
    QuestionService questionService;

    @Autowired
    SensetiveService senService;

    @Autowired
    HostHolder hostholeder;//上下文存入用户

    @RequestMapping(value = "/addComment", method = {RequestMethod.POST})
    public String addComment(@RequestParam("questionId") int questionId,
                             @RequestParam("content") String content) {
        try {
            content = HtmlUtils.htmlEscapeHex(content);
            content = senService.filter(content);
            //构建Comment
            //(content,user_id,entity_id,entity_type,created_date,status)
            Comment comment = new Comment();
            comment.setContent(content);
            User user = hostholeder.getUser();
            if (user == null) {
                comment.setUserid(DEFAULT_USER_ID);
            } else {
                comment.setUserid(user.getId());
            }
            comment.setEntityid(questionId);
            comment.setEntitytype(1);//1---question,2---comment
            comment.setCreateddate(new Date());
            comment.setStatus(0);

            commentService.addComment(comment);

            //增加评论数目
            int count=questionService.getCommentCount(questionId);
            questionService.increaseCommentCount(count+1,questionId);

        } catch (Exception e) {
            logger.error("添加评论失败", e.getMessage());
        }

        //不会写
        return "redirect:/question/" + String.valueOf(questionId);
    }
}
