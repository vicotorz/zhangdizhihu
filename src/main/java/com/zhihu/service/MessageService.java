package com.zhihu.service;

import com.zhihu.dao.MessageDAO;
import com.zhihu.dao.QuestionDAO;
import com.zhihu.model.HostHolder;
import com.zhihu.model.Message;
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
public class MessageService {
    private static final Logger logger = LoggerFactory.getLogger(MessageService.class);
    @Autowired
    MessageDAO messageDAO;

    @Autowired
    SensetiveService senService;

    @Autowired
    HostHolder hostHolder;

    //增加消息
    public int addMessage(Message message) {
        //Html过滤
        message.setContent(HtmlUtils.htmlEscape(message.getContent()));
        //过滤敏感词
        message.setContent(senService.filter(message.getContent()));
        return messageDAO.addMessage(message) > 0 ? message.getId() : 0;
    }

    public List<Message> getConversationList(int userid, int offset, int limit) {
        return messageDAO.getConversationList(userid,offset,limit);
    }

    public List<Message> getConversationDetail(String conversationId, int offset, int limit) {
        return messageDAO.getConversationDetail(conversationId,offset,limit);
    }

    public int getConvesationUnreadCount(int userid, String conversationid) {
        return messageDAO.getConvesationUnreadCount(userid,conversationid);
    }
}
