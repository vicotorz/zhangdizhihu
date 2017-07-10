package com.zhihu.controller;

import com.zhihu.Utils.JsonUtil;
import com.zhihu.model.*;
import com.zhihu.service.*;
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
    HostHolder hostHolder;

    @Autowired
    LikeService likeService;

    @Autowired
    CommentService commentService;

    @Autowired
    UserService userService;

    @Autowired
    FollowService followService;

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

        List<Comment> commentList = commentService.getCommentsByEntity(qid, EntityType.ENTITY_QUESTION);
        List<ViewObject> vos = new ArrayList<>();
        for (Comment comment : commentList) {
            ViewObject vo = new ViewObject();
            vo.set("comment", comment);
            //没有返回comment内容
            System.out.println("评论信息");
            System.out.println("评论内容:" + comment.getContent() + "评论用户id:" + comment.getUserid() + " 评论时间:" + comment.getCreateddate());
            System.out.println(comment.getEntityid() + "--" + comment.getEntitytype() + "--" + comment.getStatus());

//            private int id;//评论id
//            private String content;//评论内容
//            private int user_id;//评论用户id
//            private int entity_id;//--->questionId  & commentId
//            private int entity_type;//question & comment
//            private Date created_date;
//            private int status;//被删除状态
            //////////////////////////////////添加点赞信息//////////////////
            if (hostHolder.getUser() == null) {
                vo.set("liked", 0);
            } else {
                vo.set("liked", likeService.getLikeStatus(hostHolder.getUser().getId(), EntityType.ENTITY_COMMENT, comment.getId()));
            }

            vo.set("likeCount", likeService.getLikeCount(EntityType.ENTITY_COMMENT, comment.getId()));

            vo.set("user", userService.getUser(comment.getUserid()));
            vos.add(vo);
        }
        model.addAttribute("VOcomments", vos);

        //////////////////添加关注信息
        List<ViewObject> followUsers = new ArrayList<>();

        //获得关注这个问题的用户信息--把关注的用户都找到-->再一个一个取出来
        List<Integer> users = followService.getFollowers(EntityType.ENTITY_QUESTION, qid, 20);

        for (Integer userId : users) {
            ViewObject vo = new ViewObject();
            User u = userService.getUser(userId);
            if (u == null) {
                continue;
            }
            vo.set("name", u.getName());
            vo.set("headUrl", u.getHeadUrl());
            vo.set("id", u.getId());
            followUsers.add(vo);
        }
        model.addAttribute("followUsers", followUsers);
        if (hostholeder.getUser() != null) {
            //判断当前用户是不是关注了这个问题
            model.addAttribute("followed", followService.isFollower(hostholeder.getUser().getId(), EntityType.ENTITY_QUESTION, qid));
        } else {
            model.addAttribute("followed", false);
        }
        return "detail";
    }
}
