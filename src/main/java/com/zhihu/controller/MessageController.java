package com.zhihu.controller;

import com.zhihu.Utils.JsonUtil;
import com.zhihu.model.HostHolder;
import com.zhihu.model.Question;
import com.zhihu.service.MessageService;
import com.zhihu.service.QuestionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

/**
 * Created by dell on 2017/6/10
 */
@Controller
public class MessageController {
    private static final Logger logger = LoggerFactory.getLogger(MessageController.class);

    @Autowired
    MessageService messageservice;

    @Autowired
    HostHolder hostholeder;//上下文存入用户

    @RequestMapping(value = "/message/add", method = {RequestMethod.POST})
    @ResponseBody
    public String addMessage(@RequestParam("title") String title, @RequestParam("content") String content) {
        return "redirect:/message";
    }

}
