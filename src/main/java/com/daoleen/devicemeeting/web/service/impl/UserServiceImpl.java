package com.daoleen.devicemeeting.web.service.impl;

import com.daoleen.devicemeeting.web.domain.User;
import com.daoleen.devicemeeting.web.repository.UserRepository;
import com.daoleen.devicemeeting.web.service.UserService;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by alex on 17.6.14.
 */

@Repository
@Transactional
public class UserServiceImpl implements UserService {
    private final static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;


    @PersistenceContext
    private EntityManager entityManager;


    @Override
    @Transactional(readOnly = true)
    public List<User> findAll() {
        return Lists.newArrayList(userRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public User findById(Long id) {
        return userRepository.findOne(id);
    }

    @Override
    @Transactional(readOnly = true)
    public User findByEmail(String email) {
        logger.debug("Finding user by email: {}", email);

        //noinspection JpaQlInspection
        User foundUser = entityManager.createQuery("select u from User u where u.email = :email", User.class)
                .setParameter("email", email)
                .getSingleResult();
        logger.debug("Found user: {}", foundUser);
        logger.debug("User roles: {}", foundUser.getRoles());
        return foundUser;
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }
}
