package com.exampel.myMail.controller;

import com.exampel.myMail.model.User;
import com.exampel.myMail.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.Map;

@Controller
public class AllController {
    @Autowired
    private UserService userService;

    @GetMapping("/")
    public ModelAndView greeting(Principal principal) {
        ModelAndView modelAndView = new ModelAndView("greeting");
        if (principal != null){
            Map details = (Map)((OAuth2Authentication) principal).getUserAuthentication().getDetails();
            User newUser = new User();
            newUser.setLogin((String)details.get("email"));
            newUser.setPassword((String)details.get("id"));
            String result = userService.clientAddUser(newUser);

            if (result.equals("Добавлен новый пользователь") || result.equals("Пользователь " + newUser.getLogin() + " уже существует")){
                String auth = userService.login(newUser);
                modelAndView.setViewName("login");
                modelAndView.addObject("auth", auth);
            }
        }

        return modelAndView;
    }

    @GetMapping(value="/login2")
    public String login(){
        return "login";
    }

    @GetMapping(value="/register")
    public String register(){
        return "register";
    }

    @GetMapping(value = "/addMessage")
    public String addMessage(){
        return "addMessage";
    }

    @GetMapping(path = "/addUser")
    public String addUser() {
        return "addUser";
    }

    @GetMapping(path = "/allMessage")
    public String allMessage() {
        return "allMessage";
    }

    @GetMapping (path = "/all")
    public String all() {
        return "all";
    }
}