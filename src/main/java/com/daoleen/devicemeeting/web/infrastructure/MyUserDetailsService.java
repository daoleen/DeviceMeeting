package com.daoleen.devicemeeting.web.infrastructure;

import com.daoleen.devicemeeting.web.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.annotation.Resource;

/**
 * Created by alex on 18.6.14.
 */
public class MyUserDetailsService implements UserDetailsService {

    @Resource(name = "userService")
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userService.findByEmail(email);
    }
}
