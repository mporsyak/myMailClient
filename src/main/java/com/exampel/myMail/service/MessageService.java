package com.exampel.myMail.service;


import com.exampel.myMail.model.MessageDto;
import com.exampel.myMail.model.Message;
import com.exampel.myMail.model.NewMessageDto;
import com.exampel.myMail.model.User;
import com.exampel.myMail.repository.MessageRepository;
import com.exampel.myMail.util.ServerUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class MessageService {
    @Autowired
    MessageRepository messageRepository;

    private RestTemplate restTemplate;

    public MessageService(RestTemplateBuilder templateBuilder){
        restTemplate = templateBuilder.build();
    }

    private HttpEntity<String> getEntity(User user){
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);

        String auth = user.getLogin() + ":" + user.getPassword();
        byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII")));
        String authHeader = "Basic " + new String(encodedAuth);
        headers.set("Authorization", authHeader);
        HttpEntity<String> entity = new HttpEntity<String>(headers);

        return entity;
    }

    public String getAllIncomeMessagesWithTemplate(User user){
        ResponseEntity<String> response = restTemplate.exchange(ServerUtils.SERVER_HOSTNAME + "server/showIncomeMessages/" + user.getLogin(), HttpMethod.GET, getEntity(user), String.class);
        return response.getBody();
    }

    public String getAllOutcomeMessagesWithTemplate(User user){
        ResponseEntity<String> response = restTemplate.exchange(ServerUtils.SERVER_HOSTNAME + "showOutcomeMessages/" + user.getLogin(), HttpMethod.GET, getEntity(user), String.class);
        return response.getBody();
    }

    public String clientAddMessage(User user, NewMessageDto message){
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);

        String auth = user.getLogin() + ":" + user.getPassword();
        byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII")));
        String authHeader = "Basic " + new String(encodedAuth);
        headers.set("Authorization", authHeader);

        ObjectMapper objectMapper = new ObjectMapper();
        String messageJson = "";
        try {
            messageJson = objectMapper.writeValueAsString(message);
        } catch (JsonProcessingException e) {
        }

        HttpEntity<String> entity = new HttpEntity<String>(messageJson, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(ServerUtils.SERVER_HOSTNAME + "server/sendMessage/", entity, String.class);
        return response.getBody();
    }

    public String clientGetAllMessageByUser(User userSender, User userRecip){
        ResponseEntity<String> response = restTemplate.exchange(ServerUtils.SERVER_HOSTNAME + "server/allMessages/" + userRecip.getLogin(), HttpMethod.GET, getEntity(userSender), String.class);
        return response.getBody();
    }




















    public Message getMessage(String content){return messageRepository.findByContent(content);}

    public List<MessageDto> getAllIncomeMessages(String authUserLogin){
        return getAllDirectMessages(false, authUserLogin);
    }

    public List<MessageDto> getAllOutcomeMessages(String authUserLogin){
        return getAllDirectMessages(true, authUserLogin);
    }

    private List<MessageDto> getAllDirectMessages(boolean isOutcomeDirect, String authUserLogin){
        List<Message> allMessageList = getAllMessage();

        List<MessageDto> messages = new ArrayList();
        for (int i = 0; i < allMessageList.size(); i++) {
            Message currentMessage = allMessageList.get(i);

            if (isOutcomeDirect ? currentMessage.getUserSender().getLogin().equals(authUserLogin) : currentMessage.getUserRecip().getLogin().equals(authUserLogin)){
                MessageDto messageInfo = new MessageDto();
                messageInfo.setContent(currentMessage.getContent());
                messageInfo.setGoal((isOutcomeDirect ? ("Получатель: " + currentMessage.getUserRecip().getLogin()) : ("Отправитель: " + currentMessage.getUserSender().getLogin())));
                messages.add(messageInfo);
            }
        }

        return messages;
    }

    public List<Message> getAllMessage(){
        return (List<Message>) messageRepository.findAll();
    }



}
