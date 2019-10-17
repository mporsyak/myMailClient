package com.exampel.myMail.util;

import com.exampel.myMail.model.User;
import javafx.util.Pair;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.nio.charset.Charset;
import java.util.Arrays;

public class RestTemplateUtils {

    public static HttpHeaders getHeaders(){
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);

        return headers;
    }

    public static HttpHeaders getHeaders(String encodedAuthStr){
        HttpHeaders headers = getHeaders();

        String authHeader = "Basic " + encodedAuthStr;
        headers.set("Authorization", authHeader);

        return headers;
    }

    public static Pair<String, HttpHeaders> getHeaders(User user){
        String auth = user.getLogin() + ":" + user.getPassword();
        byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII")));
        String encodedAuthStr = new String(encodedAuth);
        HttpHeaders headers = getHeaders(encodedAuthStr);

        return new Pair<>(encodedAuthStr, headers);
    }
}
