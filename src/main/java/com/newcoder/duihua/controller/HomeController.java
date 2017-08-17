package com.newcoder.duihua.controller;

import com.newcoder.duihua.model.Question;
import com.newcoder.duihua.model.ViewObject;
import com.newcoder.duihua.service.QuestionService;
import com.newcoder.duihua.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangdong on 2017/8/3.
 */
@Controller

public class HomeController {
    @Autowired
    UserService userService;

    @Autowired
    QuestionService questionService;

    @RequestMapping(path = {"/","index"},method = RequestMethod.GET)
    public String index(Model model) {
        List<Question> questions = questionService.getLatestQuestions(0,0,10);
        //model.addAttribute("questions",questions);
        List<ViewObject> vos = new ArrayList<>();
        for (Question question:questions
             ) {
            ViewObject vo = new ViewObject();
            vo.set("question",question);
            vo.set("user",userService.getUser(question.getUserId()));
            vos.add(vo);
        }
        model.addAttribute("vos",vos);
        return "index";
    }
}
