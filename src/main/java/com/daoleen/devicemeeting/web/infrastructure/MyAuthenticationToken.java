package com.daoleen.devicemeeting.web.infrastructure;

import com.daoleen.devicemeeting.web.domain.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

/**
 * Created by alex on 19.6.14.
 */
public class MyAuthenticationToken extends UsernamePasswordAuthenticationToken {
    private Long id;

    public MyAuthenticationToken(User user) {
        super(user, user.getPassword(), user.getRoles());
        setId(user.getId());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
