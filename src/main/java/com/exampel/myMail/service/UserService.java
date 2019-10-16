package com.exampel.myMail.service;

import com.exampel.myMail.model.User;

import com.exampel.myMail.util.ServerUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.util.Pair;
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

    private HttpHeaders getHeaders(){
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);

        return headers;
    }

    private HttpHeaders getHeaders(String encodedAuthStr){
        String authHeader = "Basic " + encodedAuthStr;
        getHeaders().set("Authorization", authHeader);

        return getHeaders();
    }

    private Pair<String, HttpHeaders> getHeaders(User user){
        String auth = user.getLogin() + ":" + user.getPassword();
        byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII")));
        String encodedAuthStr = new String(encodedAuth);
        getHeaders(encodedAuthStr);

        return new Pair<>(encodedAuthStr, getHeaders(encodedAuthStr));
    }

    public String clientAddUser(User user) {
        ObjectMapper objectMapper = new ObjectMapper();
        String userJson = "";
        try {
            userJson = objectMapper.writeValueAsString(user);
        } catch (JsonProcessingException e) {
        }

        HttpEntity<String> entity = new HttpEntity<String>(userJson, getHeaders());

        ResponseEntity<String> response = restTemplate.postForEntity(ServerUtils.SERVER_HOSTNAME + "server/addUser", entity, String.class);
        return response.getBody();
    }

    public String clientGetAllUser(String encodedAuthStr) {
        HttpEntity<String> entity = new HttpEntity<String>(getHeaders(encodedAuthStr));

        ResponseEntity<String> response = restTemplate.exchange(ServerUtils.SERVER_HOSTNAME + "server/allUsers", HttpMethod.GET, entity, String.class);
        return response.getBody();
    }

    public String login(User user) {
        Pair<String, HttpHeaders> pair = getHeaders(user);

        HttpEntity<String> entity = new HttpEntity<String>(pair.getValue());

        ResponseEntity<String> response = restTemplate.exchange(ServerUtils.SERVER_HOSTNAME, HttpMethod.GET, entity, String.class);
        return pair.getKey();
    }
}
