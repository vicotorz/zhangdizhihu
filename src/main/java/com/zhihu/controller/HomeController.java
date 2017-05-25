package com.zhihu.controller;

import com.zhihu.model.Question;
import com.zhihu.model.ViewObject;
import com.zhihu.service.QuestionService;
import com.zhihu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dell on 2017/5/23.
 */
@Controller
public class HomeController {
    @Autowired
    UserService userService;

    @Autowired
    QuestionService questionService;

    @RequestMapping({"/", "/index"})
    public String index(Model model) {
        //读10个问题--ViewObject组合每一个用户+问题
        List<Question> list = questionService.getLatestQuestions(0, 0, 10);
        List<ViewObject> vos = new ArrayList<ViewObject>();

        for (Question question : list) {
            ViewObject vo = new ViewObject();
            vo.set("question", question);
            vo.set("user", userService.getUser(question.getUserId()));
            vos.add(vo);
        }
        model.addAttribute("vos", vos);
        return "index";
    }

}
