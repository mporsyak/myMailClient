package com.exampel.myMail.service;

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
public class UserService {

    RestTemplate restTemplate;

    public UserService(RestTemplateBuilder templateBuilder){
        restTemplate = templateBuilder.build();
    }

    public String clientAddUser(User user) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);

        ObjectMapper objectMapper = new ObjectMapper();
        String userJson = "";
        try {
            userJson = objectMapper.writeValueAsString(user);
        } catch (JsonProcessingException e) {
        }

        HttpEntity<String> entity = new HttpEntity<String>(userJson, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(ServerUtils.SERVER_HOSTNAME + "server/addUser", entity, String.class);
        return response.getBody();
    }

    public String clientGetAllUser(String auth) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);

        String authHeader = "Basic " + auth;
        headers.set("Authorization", authHeader);

        HttpEntity<String> entity = new HttpEntity<String>(headers);

        ResponseEntity<String> response = restTemplate.exchange(ServerUtils.SERVER_HOSTNAME + "server/allUsers", HttpMethod.GET, entity, String.class);
        return response.getBody();
    }

    public String login(User user) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);

        String auth = user.getLogin() + ":" + user.getPassword();
        byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII")));
        String authHeader = "Basic " + new String(encodedAuth);
        headers.set("Authorization", authHeader);

        HttpEntity<String> entity = new HttpEntity<String>( headers);

        ResponseEntity<String> response = restTemplate.exchange(ServerUtils.SERVER_HOSTNAME, HttpMethod.GET, entity, String.class);
        return new String(encodedAuth);
    }
}
