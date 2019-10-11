package com.exampel.myMail.repository;
import com.exampel.myMail.model.User;
import  org.springframework.data.repository.CrudRepository ;
import  org.springframework.stereotype.Repository ;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Long> {

   User findByLogin(String name);

}