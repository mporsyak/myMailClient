package com.exampel.myMail.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class AllController { //TODO: server-side rendering and client-side rendering
    @GetMapping("/")
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