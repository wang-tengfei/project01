package com.example.test.service;

import com.example.test.domain.User;

/**
 * @author: Tengfei Wang
 * @description:
 * @date: Created in 14:03 2019-05-14
 * @modified by:
 */
public interface UserService {

    User saveUser(User user);

    User getUser(String id);

    Object getUserByRest(String id);

    Object getEsInfo();
}
