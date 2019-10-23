package com.exampel.myMail.service;

import com.exampel.myMail.config.AuthorizationHttpHeaders;
import com.exampel.myMail.model.User;

import com.exampel.myMail.util.ServerUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;

@Service
public class UserService {

    @Autowired
    private RestTemplate restTemplate;

    public String clientAddUser(User user) {
        ObjectMapper objectMapper = new ObjectMapper();
        String userJson = "";
        try {
            userJson = objectMapper.writeValueAsString(user);
        } catch (JsonProcessingException e) {
        }

        HttpEntity<String> entity = new HttpEntity<>(userJson);

        ResponseEntity<String> response = restTemplate.postForEntity(ServerUtils.SERVER_HOSTNAME + "server/addUser", entity, String.class);
        return response.getBody();
    }

    public String clientGetAllUser(String encodedAuthStr) {
        HttpHeaders httpHeaders = new AuthorizationHttpHeaders(encodedAuthStr);
        HttpEntity<String> entity = new HttpEntity<>(httpHeaders);

        ResponseEntity<String> response = restTemplate.exchange(ServerUtils.SERVER_HOSTNAME + "server/allUsers", HttpMethod.GET, entity, String.class);
        return response.getBody();
    }

    public String login(User user) {
        String auth = user.getLogin() + ":" + user.getPassword();
        byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII")));
        String encodedAuthStr = new String(encodedAuth);

        HttpHeaders httpHeaders = new AuthorizationHttpHeaders(encodedAuthStr);
        HttpEntity<String> entity = new HttpEntity<>(httpHeaders);

        restTemplate.exchange(ServerUtils.SERVER_HOSTNAME, HttpMethod.GET, entity, String.class);
        return encodedAuthStr;
    }
}
