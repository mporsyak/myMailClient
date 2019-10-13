package com.exampel.myMail.controller;

import com.exampel.myMail.model.NewMessageDto;
import com.exampel.myMail.model.User;
import com.exampel.myMail.service.MessageService;
import com.exampel.myMail.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;

@RestController
public class ClientMessageRestController {

    @Autowired
   private MessageService messageService;

    @Autowired
   private UserService userService;

    @GetMapping(value = "client/showIncomeMessages")
    public String showIncomeMessagesWithTemplate(Principal principal){
        User user = userService.findByLogin(principal.getName());
        return messageService.getAllIncomeMessagesWithTemplate(user);
    }

    @GetMapping(value = "client/showOutcomeMessages")
    public String showOutcomeMessagesWithTemplate(Principal principal){
        User user = userService.findByLogin(principal.getName());
        return messageService.getAllOutcomeMessagesWithTemplate(user);
    }

    @PostMapping(value = "client/sendMessage")
    public String clientSendMessage(Principal principal, @RequestBody NewMessageDto newMessage) {
        User userSender = userService.findByLogin(principal.getName());
        newMessage.setUserSender(userSender.getLogin());

        return messageService.clientAddMessage(userSender, newMessage);
    }

    @GetMapping (path = "client/allMessages/{user}")
    public String clientAllMessages(@PathVariable String user, Principal principal) {
        User userSender = userService.findByLogin(principal.getName());
        User userRecip = userService.findByLogin(user);

        return messageService.clientGetAllMessageByUser(userSender, userRecip);
    }
}
