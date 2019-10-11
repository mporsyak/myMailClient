package com.exampel.myMail.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication//(scanBasePackages={"com.example.something", "com.example.application"})
@EnableAutoConfiguration
@ComponentScan(basePackages = {"com.exampel.myMail", "com.exampel.myMail.controller"})
@EnableJpaRepositories(basePackages = {"com.exampel.myMail.repository"})
@EnableTransactionManagement
@EntityScan(basePackages = "com.exampel.myMail.model")


public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}