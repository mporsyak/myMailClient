package com.exampel.myMail.controller;

import com.exampel.myMail.model.MessageDto;
import com.exampel.myMail.model.Message;
import com.exampel.myMail.model.SendMessage;
import com.exampel.myMail.model.User;
import com.exampel.myMail.service.MessageService;
import com.exampel.myMail.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
public class ClientMessageRestController {

    @Autowired
   private MessageService messageService;

    @Autowired
   private UserService userService;

    @PostMapping(value = "/sendMessage")
    public ResponseEntity<?> sendMessage(Principal principal,
                             @RequestBody SendMessage sendMessage
    ) {
        User userRecipient = userService.findByLogin(sendMessage.getMyRecipLogin());
        User userSender = userService.findByLogin(principal.getName());

        if(userRecipient != null){
            Message message = new Message();
            message.setContent(sendMessage.getMyContent());
            message.setUserSender(userSender);
            message.setUserRecip(userRecipient);
            message.setMsgTime(new Date());
            messageService.addMessage(message);

            return new ResponseEntity<>("Сообщение успешно отправлено", HttpStatus.OK);
        }

        return new ResponseEntity<>("Пользователь " + sendMessage.getMyRecipLogin() + " не найден", HttpStatus.BAD_REQUEST);
    }

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

    @GetMapping (path = "/allMessages/{user}")
    public List<MessageDto> allMessages(@PathVariable String user, Principal principal) {
        User userSender = userService.findByLogin(principal.getName());
        User userRecip = userService.findByLogin(user);

        List<Message> senderMessages = messageService.getAllMessageByUser(userSender, userRecip);
        List<Message> recipMessages = messageService.getAllMessageByUser(userRecip, userSender);

        List<MessageDto> result = new ArrayList<>();

        for (Message message : senderMessages){
            MessageDto  messageDto = new MessageDto();
            messageDto.setContent(message.getContent());
            messageDto.setMyMsg(true);
            messageDto.setCreateMsgTime(message.getMsgTime());

            result.add(messageDto);
        }

        for (Message message : recipMessages){
            MessageDto  messageDto = new MessageDto();
            messageDto.setContent(message.getContent());
            messageDto.setMyMsg(false);
            messageDto.setCreateMsgTime(message.getMsgTime());

            result.add(messageDto);
        }

        result.sort((a,b) -> a.getCreateMsgTime().compareTo(b.getCreateMsgTime()));
        return result;
    }
}
