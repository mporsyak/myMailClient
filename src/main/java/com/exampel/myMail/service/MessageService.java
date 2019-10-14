package com.exampel.myMail.service;


import com.exampel.myMail.model.NewMessageDto;
import com.exampel.myMail.model.User;
import com.exampel.myMail.util.ServerUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.Arrays;

@Service
public class MessageService {

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

    public String getAllIncomeMessagesWithTemplate(String auth){
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);

        String authHeader = "Basic " + auth;
        headers.set("Authorization", authHeader);

        HttpEntity<String> entity = new HttpEntity<String>(headers);

        ResponseEntity<String> response = restTemplate.exchange(ServerUtils.SERVER_HOSTNAME + "server/showIncomeMessages", HttpMethod.GET, entity, String.class);
        return response.getBody();
    }

    public String getAllOutcomeMessagesWithTemplate(String auth){
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);

        String authHeader = "Basic " + auth;
        headers.set("Authorization", authHeader);

        HttpEntity<String> entity = new HttpEntity<String>(headers);

        ResponseEntity<String> response = restTemplate.exchange(ServerUtils.SERVER_HOSTNAME + "server/showOutcomeMessages", HttpMethod.GET, entity, String.class);
        return response.getBody();
    }

    public String clientAddMessage(NewMessageDto message, String auth){
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);

        ObjectMapper objectMapper = new ObjectMapper();
        String messageJson = "";
        try {
            messageJson = objectMapper.writeValueAsString(message);
        } catch (JsonProcessingException e) {
        }

        String authHeader = "Basic " + auth;
        headers.set("Authorization", authHeader);

        HttpEntity<String> entity = new HttpEntity<String>(messageJson, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(ServerUtils.SERVER_HOSTNAME + "server/sendMessage/", entity, String.class);
        return response.getBody();
    }

    public String clientGetAllMessageByUser(String userRecip, String auth){
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);

        String authHeader = "Basic " + auth;
        headers.set("Authorization", authHeader);

        HttpEntity<String> entity = new HttpEntity<String>(headers);

        ResponseEntity<String> response = restTemplate.exchange(ServerUtils.SERVER_HOSTNAME + "server/allMessages/" + userRecip, HttpMethod.GET, entity, String.class);
        return response.getBody();
    }
}
