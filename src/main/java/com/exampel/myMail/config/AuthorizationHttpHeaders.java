package com.exampel.myMail.config;

import org.springframework.http.HttpHeaders;

public class AuthorizationHttpHeaders extends HttpHeaders {
    public AuthorizationHttpHeaders(String encodedAuthStr) {
        super();
        this.set("Authorization", "Basic " + encodedAuthStr);
    }
}
