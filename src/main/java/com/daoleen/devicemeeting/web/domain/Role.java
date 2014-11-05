package com.daoleen.devicemeeting.web.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.security.core.GrantedAuthority;

/**
 * Created by alex on 17.6.14.
 */

@Entity
@Table(name = "roles")
public class Role implements GrantedAuthority, Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -8044606535331513887L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @NotNull
    @NotEmpty
    @Size(min = 5, max = 32)
    @Column(name = "role", unique = true, nullable = false, length = 32)
    private String authority;


    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "roles")
    private List<User> users;


    public Role() {
    }

    public Role(Integer id, String authority) {
        this.id = id;
        this.authority = authority;
    }

    // Getters / Setters
    @Override
    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
