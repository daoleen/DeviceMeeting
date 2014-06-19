package com.daoleen.devicemeeting.web.viewmodel;

import com.daoleen.devicemeeting.web.validator.annotation.SamePassword;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created by alex on 18.6.14.
 */

@SamePassword
public class Passwords {

    @NotEmpty
    @Length(min = 4, max = 32)
    private String password;

    private String passwordAgain;

    public Passwords() {
    }

    public Passwords(String password, String passwordAgain) {
        this.password = password;
        this.passwordAgain = passwordAgain;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordAgain() {
        return passwordAgain;
    }

    public void setPasswordAgain(String passwordAgain) {
        this.passwordAgain = passwordAgain;
    }
}