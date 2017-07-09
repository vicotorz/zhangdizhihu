package com.zhihu.service;

import com.zhihu.dao.CommentDAO;
import com.zhihu.dao.QuestionDAO;
import com.zhihu.model.Comment;
import com.zhihu.model.HostHolder;
import com.zhihu.model.Question;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import java.util.List;

/**
 * Created by dell on 2017/5/23.
 */
@Service
public class CommentService {
    private static final Logger logger = LoggerFactory.getLogger(CommentService.class);
    @Autowired
    CommentDAO commentDao;

    @Autowired
    SensetiveService senService;

    @Autowired
    HostHolder hostHolder;


    //增加评论
    public int addComment(Comment comment) {
        //Html过滤
        comment.setContent(HtmlUtils.htmlEscape(comment.getContent()));
        //过滤敏感词
        comment.setContent(senService.filter(comment.getContent()));
        return commentDao.addComment(comment) > 0 ? comment.getId() : 0;
    }

    //找出qid问题下的所有评论
    public List<Comment> getCommentsByEntity(int entity_id, int entity_type) {
        System.out.println("++++++查看List里面的内容++++++++");
        List<Comment> list = commentDao.getCommentsByEntity(entity_id, entity_type);
        for (int i = 0; i < list.size(); i++) {
            Comment c = list.get(i);
            System.out.println(c.getId() + "---" + c.getContent() + "---" + c.getEntity_id() + "---" + c.getCreated_date() + "---"
                    + c.getEntity_type() + "---" + c.getStatus()+"---"+c.getUser_id());
        }
        return commentDao.getCommentsByEntity(entity_id, entity_type);
    }

    //获得具体id的comment内容
    public Comment getCommentById(int commentid){
        return commentDao.getCommentById(commentid);
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //获得CommentCount
    public int getUserCommentCount(int userId) {
        return commentDao.getUserCommentCount(userId);
    }
}
