package com.exampel.myMail.controller;

import com.exampel.myMail.model.NewMessageDto;
import com.exampel.myMail.service.MessageService;
import com.exampel.myMail.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class MessageRestController {

    @Autowired
   private MessageService messageService;

    @Autowired
   private UserService userService;

    @GetMapping(value = "client/showIncomeMessages/{auth}")
    public String showIncomeMessagesWithTemplate(@PathVariable String auth){
        return messageService.getAllIncomeMessagesWithTemplate(auth);
    }

    @GetMapping(value = "client/showOutcomeMessages/{auth}")
    public String showOutcomeMessagesWithTemplate(@PathVariable String auth){
        return messageService.getAllOutcomeMessagesWithTemplate(auth);
    }

    @PostMapping(value = "client/sendMessage/{auth}")
    public String clientSendMessage(@PathVariable String auth, @RequestBody NewMessageDto newMessage) {
        return messageService.clientAddMessage(newMessage, auth);
    }

    @GetMapping (path = "client/allMessages/{userRecip}/{auth}")
    public String clientAllMessages(@PathVariable String userRecip, @PathVariable String auth) {
        return messageService.clientGetAllMessageByUser(userRecip, auth);
    }
}
