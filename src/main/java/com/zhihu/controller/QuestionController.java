package com.zhihu.controller;

import com.zhihu.Utils.JsonUtil;
import com.zhihu.model.*;
import com.zhihu.service.CommentService;
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
    QuestionService questionService;

    @Autowired
    CommentService commentService;

    @Autowired
    UserService userService;

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

            if (questionService.addQuestion(question) > 0) {
                //成功
                return JsonUtil.getJSONString(0);
            }
        } catch (Exception e) {
            logger.error("增加题目失败" + e.getMessage());
        }
        return JsonUtil.getJSONString(1, "增加失败");
    }

    @RequestMapping(value = "/question/{qid}", method = {RequestMethod.GET})
    public String questionDetail(Model model, @PathVariable("qid") int qid) {
        Question question = questionService.getById(qid);
        model.addAttribute("question", question);
        System.out.println("@@@%%%%%%"+qid+"%%%%%%%%%%%");

        List<Comment> commentList = commentService.getCommentsByEntity(qid, EntityType.ENTITY_QUESTION);
        List<ViewObject> vos = new ArrayList<>();
        for (Comment comment : commentList) {
            ViewObject vo = new ViewObject();
            vo.set("comment", comment);

//            private int id;
//            private String content;
//            private int user_id;
//            private int entity_id;
//            private int entity_type;
//            private Date created_date;
//            private int status;

            //没有返回comment内容
            System.out.println("评论内容"+comment.getContent()+"评论用户id"+comment.getUserid()+" 评论时间"+comment.getCreateddate());
            System.out.println(comment.getEntityid()+"--"+comment.getEntitytype()+"--"+comment.getStatus());
            vo.set("user", userService.getUser(comment.getUserid()));
            vos.add(vo);
        }
        model.addAttribute("voComments", vos);

        return "detail";
    }
}
