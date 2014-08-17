package com.daoleen.devicemeeting.web.service.impl;

import com.daoleen.devicemeeting.web.domain.User;
import com.daoleen.devicemeeting.web.domain.UserDetails;
import com.daoleen.devicemeeting.web.repository.UserDetailsRepository;
import com.daoleen.devicemeeting.web.repository.UserRepository;
import com.daoleen.devicemeeting.web.service.UserDetailsService;
import com.daoleen.devicemeeting.web.service.UserService;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by alex on 14.7.14.
 */

@Repository
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserDetailsRepository userDetailsRepository;

    @Override
    @Transactional(readOnly = true)
    public List<UserDetails> findAll() {
        return Lists.newArrayList(userDetailsRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails findById(Long id) {
        return userDetailsRepository.findOne(id);
    }

    @Override
    public UserDetails save(UserDetails userDetails) {
        return userDetailsRepository.save(userDetails);
    }
}
