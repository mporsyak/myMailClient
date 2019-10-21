package com.exampel.myMail.service;

import com.exampel.myMail.model.User;

import com.exampel.myMail.util.ServerUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;

@Service
public class UserService {

    RestTemplate restTemplate;

    @Autowired
    private BeanFactory beanFactory;

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

        HttpHeaders httpHeaders = beanFactory.getBean(HttpHeaders.class);
        HttpEntity<String> entity = new HttpEntity<String>(userJson, httpHeaders);

        ResponseEntity<String> response = restTemplate.postForEntity(ServerUtils.SERVER_HOSTNAME + "server/addUser", entity, String.class);
        return response.getBody();
    }

    public String clientGetAllUser(String encodedAuthStr) {
        HttpHeaders httpHeaders = beanFactory.getBean(HttpHeaders.class, encodedAuthStr);
        HttpEntity<String> entity = new HttpEntity<String>(httpHeaders);

        ResponseEntity<String> response = restTemplate.exchange(ServerUtils.SERVER_HOSTNAME + "server/allUsers", HttpMethod.GET, entity, String.class);
        return response.getBody();
    }

    public String login(User user) {
        String auth = user.getLogin() + ":" + user.getPassword();
        byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII")));
        String encodedAuthStr = new String(encodedAuth);

        HttpHeaders httpHeaders = beanFactory.getBean(HttpHeaders.class, encodedAuthStr);
        HttpEntity<String> entity = new HttpEntity<String>(httpHeaders);

        restTemplate.exchange(ServerUtils.SERVER_HOSTNAME, HttpMethod.GET, entity, String.class);
        return encodedAuthStr;
    }
}
