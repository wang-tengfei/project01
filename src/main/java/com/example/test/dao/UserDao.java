package com.example.test.dao;

import com.example.test.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author: Tengfei Wang
 * @description:
 * @date: Created in 14:01 2019-05-14
 * @modified by:
 */
public interface UserDao extends MongoRepository<User, String> {

    User findFirstById(String id);
}
