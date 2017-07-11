package com.zhihu.async.handler;


import com.zhihu.async.EventHandler;
import com.zhihu.async.EventModel;
import com.zhihu.async.EventType;
import com.zhihu.dao.QuestionDAO;
import com.zhihu.model.Comment;
import com.zhihu.model.Message;
import com.zhihu.model.Question;
import com.zhihu.model.User;
import com.zhihu.service.CommentService;
import com.zhihu.service.MessageService;
import com.zhihu.service.QuestionService;
import com.zhihu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by victorz on 2017/6/25.
 */
@Component
public class LikeHandler implements EventHandler {
    @Autowired
    MessageService messageService;

    @Autowired
    UserService userService;

    @Autowired
    QuestionService questionService;

    @Autowired
    CommentService commentService;

    @Override
    public void doHandle(EventModel model) {
        Message message = new Message();
        message.setFromId(3);
        message.setToId(model.getEntityOwnerId());
        message.setCreatedDate(new Date());
        User user = userService.getUser(model.getActorId());
        Comment comment=commentService.getCommentById(model.getEntityId());
        Question question=questionService.getById(comment.getEntityid());
        message.setContent("用户" + user.getName()
                + "赞了你在帖子："+question.getContent()+" 中的评论:\n"+comment.getContent());

        messageService.addMessage(message);
    }

    @Override
    public List<EventType> getSupportEventTypes() {
        return Arrays.asList(EventType.LIKE);
    }
}
