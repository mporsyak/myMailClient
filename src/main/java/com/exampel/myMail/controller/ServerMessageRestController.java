package com.exampel.myMail.controller;

import com.exampel.myMail.model.MessageDto;
import com.exampel.myMail.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class ServerMessageRestController {
    @Autowired
   private MessageService messageService;

    @GetMapping(value = "server/showIncomeMessages/{user}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<MessageDto> showIncomeMessages(@PathVariable String user){
        return messageService.getAllIncomeMessages(user);
    }

    @GetMapping(value = "server/showOutcomeMessages/{user}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<MessageDto> showOutcomeMessages(@PathVariable String user){
        return messageService.getAllOutcomeMessages(user);
    }
}
