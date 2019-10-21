package com.exampel.myMail.service;


import com.exampel.myMail.model.NewMessageDto;
import com.exampel.myMail.util.ServerUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MessageService {

    private RestTemplate restTemplate;

    @Autowired
    BeanFactory beanFactory;

    @Autowired
    public MessageService(RestTemplateBuilder templateBuilder){
        restTemplate = templateBuilder.build();
    }

    public String getAllIncomeMessagesWithTemplate(String encodedAuthStr){
        HttpHeaders httpHeaders = beanFactory.getBean(HttpHeaders.class, encodedAuthStr);
        HttpEntity<String> entity = new HttpEntity<String>(httpHeaders);

        ResponseEntity<String> response = restTemplate.exchange(ServerUtils.SERVER_HOSTNAME + "server/showIncomeMessages", HttpMethod.GET, entity, String.class);
        return response.getBody();
    }

    public String getAllOutcomeMessagesWithTemplate(String encodedAuthStr){
        HttpHeaders httpHeaders = beanFactory.getBean(HttpHeaders.class, encodedAuthStr);
        HttpEntity<String> entity = new HttpEntity<String>(httpHeaders);

        ResponseEntity<String> response = restTemplate.exchange(ServerUtils.SERVER_HOSTNAME + "server/showOutcomeMessages", HttpMethod.GET, entity, String.class);
        return response.getBody();
    }

    public String clientAddMessage(NewMessageDto message, String encodedAuthStr){
        ObjectMapper objectMapper = new ObjectMapper();
        String messageJson = "";
        try {
            messageJson = objectMapper.writeValueAsString(message);
        } catch (JsonProcessingException e) {
        }

        HttpHeaders httpHeaders = beanFactory.getBean(HttpHeaders.class, encodedAuthStr);
        HttpEntity<String> entity = new HttpEntity<String>(messageJson, httpHeaders);

        ResponseEntity<String> response = restTemplate.postForEntity(ServerUtils.SERVER_HOSTNAME + "server/sendMessage/", entity, String.class);
        return response.getBody();
    }

    public String clientGetAllMessageByUser(String userRecip, String encodedAuthStr){
        HttpHeaders httpHeaders = beanFactory.getBean(HttpHeaders.class, encodedAuthStr);
        HttpEntity<String> entity = new HttpEntity<String>(httpHeaders);

        ResponseEntity<String> response = restTemplate.exchange(ServerUtils.SERVER_HOSTNAME + "server/allMessages/" + userRecip, HttpMethod.GET, entity, String.class);
        return response.getBody();
    }
}
