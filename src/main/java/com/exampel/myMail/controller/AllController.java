package com.exampel.myMail.controller;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
//@ComponentScan("com.exampel.myMail.service")
public class AllController { //TODO: server-side rendering and client-side rendering
    @GetMapping("/greeting")
    public String greeting() {
        return "greeting";
    }

    @GetMapping(value="/login")
    public String login(){
        return "login";
    }

    @GetMapping(value="/register")
    public String register(){
        return "register";
    }

    @GetMapping(value = "/addMessage")//TODO: Stateless and statefull
    public String addMessage(Principal principal){
//        return new ModelAndView("/addMessage", "authUser", principal.getName());
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