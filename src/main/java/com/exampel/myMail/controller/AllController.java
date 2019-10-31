package com.exampel.myMail.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class AllController {
    @GetMapping("/")
    public String greeting(Principal principal) {
        if (principal != null){
            //TODO:
        }

        return "greeting";
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