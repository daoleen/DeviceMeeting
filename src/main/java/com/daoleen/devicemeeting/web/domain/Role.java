package com.daoleen.devicemeeting.web.domain;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

/**
 * Created by alex on 17.6.14.
 */

@Entity
@Table(name = "roles")
public class Role implements GrantedAuthority, Serializable {

    @Transient
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
