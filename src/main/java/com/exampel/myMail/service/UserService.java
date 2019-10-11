package com.exampel.myMail.service;

import com.exampel.myMail.model.User;

import com.exampel.myMail.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public void addUser(User user) {userRepository.save(user);}

    public List<User> getAllUser() {return (List<User>) userRepository.findAll();}

    public User findByLogin(String login){return userRepository.findByLogin(login);}

}
