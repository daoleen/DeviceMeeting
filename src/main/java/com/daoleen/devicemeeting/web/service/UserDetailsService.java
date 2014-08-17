package com.daoleen.devicemeeting.web.service;

import com.daoleen.devicemeeting.web.domain.UserDetails;

import java.util.List;

/**
 * Created by alex on 14.7.14.
 */
public interface UserDetailsService {

    public List<UserDetails> findAll();

    public UserDetails findById(Long id);

    public UserDetails save(UserDetails userDetails);
}
