package com.exampel.myMail.controller;

import com.exampel.myMail.model.User;
import com.exampel.myMail.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserRestController {

    @Autowired
    private UserService userService;

    @PostMapping(path = "client/addUser")
    public String clientAddNewUser(@RequestBody User newUser){
        return userService.clientAddUser(newUser);
    }

    @GetMapping (path = "client/allUsers/{auth}")
    public String clientAllUsers(@PathVariable String auth) {
        return userService.clientGetAllUser(auth);
    }

    @PostMapping (path = "client/login")
    public String login(@RequestBody User user) {
        return userService.login(user);
    }
}
