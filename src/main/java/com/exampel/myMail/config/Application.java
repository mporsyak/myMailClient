package com.exampel.myMail.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication//(scanBasePackages={"com.example.something", "com.example.application"})
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class,HibernateJpaAutoConfiguration.class})
@ComponentScan(basePackages = {"com.exampel.myMail", "com.exampel.myMail.controller"})
@EntityScan(basePackages = "com.exampel.myMail.model")

public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}