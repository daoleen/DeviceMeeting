package com.daoleen.devicemeeting.web.viewmodel;

import com.daoleen.devicemeeting.web.validator.annotation.SamePassword;
import com.daoleen.devicemeeting.web.validator.annotation.UniqueField;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Size;

/**
 * Created by alex on 18.6.14.
 */
public class Account {

    @NotEmpty
    @Email
    @Size(min = 3, max = 255)
    @UniqueField(tableName = "users", fieldName = "email")
    private String email;

    @SamePassword
    private Passwords passwords;


    public Account() {
    }

    public Account(String email, Passwords passwords) {
        this.email = email;
        this.passwords = passwords;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Passwords getPasswords() {
        return passwords;
    }

    public void setPasswords(Passwords passwords) {
        this.passwords = passwords;
    }
}
