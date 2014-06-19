package com.daoleen.devicemeeting.web.service;

import com.daoleen.devicemeeting.web.domain.User;

import java.util.List;

/**
 * Created by alex on 17.6.14.
 */
public interface UserService {
    public List<User> findAll();

    public User findById(Long id);

    public User findByEmail(String email);

    public User save(User user);
}
