package com.zhihu.controller;

import com.zhihu.Utils.JsonUtil;
import com.zhihu.async.EventHandler;
import com.zhihu.async.EventModel;
import com.zhihu.async.EventProducer;
import com.zhihu.async.EventType;
import com.zhihu.model.Comment;
import com.zhihu.model.EntityType;
import com.zhihu.model.HostHolder;
import com.zhihu.service.CommentService;
import com.zhihu.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.swing.text.html.parser.Entity;

/**
 * Created by dell on 2017/6/24.
 * 不需要dao层，只需要handler放入队列即可
 */
@Controller
public class LikeController {
    @Autowired
    LikeService likeService;

    @Autowired
    HostHolder hostHolder;

    @Autowired
    CommentService commentService;

    @Autowired
    EventProducer eventProducer;

    //点赞
    @RequestMapping(path = {"/like"}, method = {RequestMethod.POST})
    @ResponseBody
    public String like(@RequestParam("commentId") int commentId) {
        if (hostHolder.getUser() == null) {
            return JsonUtil.getJSONString(999);
        }

        Comment comment = commentService.getCommentById(commentId);
        EventModel event = new EventModel(EventType.LIKE);//设置event类型
        event.setActorId(hostHolder.getUser().getId());//设置用户id
        event.setEntityId(commentId);//设置entity（评论）的id
        event.setEntityType(EntityType.ENTITY_COMMENT);//设置entity类型
        event.setEntityOwnerId(comment.getEntityid());//设置？？？
        eventProducer.fireEvent(event);

        //public long like(int userId, int entityType, int entityId)
        long likeCount = likeService.like(hostHolder.getUser().getId(), EntityType.ENTITY_COMMENT, commentId);
        return JsonUtil.getJSONString(0, String.valueOf(likeCount));

    }

    //点踩
    @RequestMapping(path = {"/dislike"}, method = {RequestMethod.POST})
    @ResponseBody
    public String dislike(@RequestParam("commentId") int commentId) {
        if (hostHolder.getUser() == null) {
            return JsonUtil.getJSONString(999);
        }
        long likeCount = likeService.disLike(hostHolder.getUser().getId(), EntityType.ENTITY_COMMENT, commentId);
        return JsonUtil.getJSONString(0, String.valueOf(likeCount));
    }


}
