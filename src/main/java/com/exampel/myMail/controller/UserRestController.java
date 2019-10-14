package com.exampel.myMail.controller;

import com.exampel.myMail.model.User;
import com.exampel.myMail.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
public class UserRestController {

    @Autowired
    private UserService userService;

    @PostMapping(path = "client/addUser")
    public String clientAddNewUser(@RequestBody User newUser){
        return userService.clientAddUser(newUser);
    }

    @GetMapping (path = "client/allUsers")
    public String clientAllUsers(Principal principal) {
        User user = userService.findByLogin(principal.getName());
        return userService.clientGetAllUser(user);
    }
}
