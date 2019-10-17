package com.exampel.myMail.service;

import com.exampel.myMail.model.User;

import com.exampel.myMail.util.RestTemplateUtils;
import com.exampel.myMail.util.ServerUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class UserService {

    RestTemplate restTemplate;

    @Autowired
    public UserService(RestTemplateBuilder templateBuilder){
        restTemplate = templateBuilder.build();
    }

    public String clientAddUser(User user) {
        ObjectMapper objectMapper = new ObjectMapper();
        String userJson = "";
        try {
            userJson = objectMapper.writeValueAsString(user);
        } catch (JsonProcessingException e) {
        }

        HttpEntity<String> entity = new HttpEntity<String>(userJson, RestTemplateUtils.getHeaders());

        ResponseEntity<String> response = restTemplate.postForEntity(ServerUtils.SERVER_HOSTNAME + "server/addUser", entity, String.class);
        return response.getBody();
    }

    public String clientGetAllUser(String encodedAuthStr) {
        HttpEntity<String> entity = new HttpEntity<String>(RestTemplateUtils.getHeaders(encodedAuthStr));

        ResponseEntity<String> response = restTemplate.exchange(ServerUtils.SERVER_HOSTNAME + "server/allUsers", HttpMethod.GET, entity, String.class);
        return response.getBody();
    }

    public String login(User user) {
        Pair<String, HttpHeaders> pair = RestTemplateUtils.getHeaders(user);

        HttpEntity<String> entity = new HttpEntity<String>(pair.getValue());

        ResponseEntity<String> response = restTemplate.exchange(ServerUtils.SERVER_HOSTNAME, HttpMethod.GET, entity, String.class);
        return pair.getKey();
    }
}
