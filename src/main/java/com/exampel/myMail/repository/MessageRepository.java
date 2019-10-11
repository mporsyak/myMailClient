package com.exampel.myMail.repository;

import com.exampel.myMail.model.Message;
import com.exampel.myMail.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MessageRepository extends CrudRepository<Message, Long>{
    Message findByContent(String content);
    List<Message> findByUserSenderAndUserRecip(User userSender, User userRecip);
}