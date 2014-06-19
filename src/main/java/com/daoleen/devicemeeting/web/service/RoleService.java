package com.daoleen.devicemeeting.web.service;

import com.daoleen.devicemeeting.web.domain.Role;

import java.util.List;

/**
 * Created by alex on 17.6.14.
 */
public interface RoleService {
    public List<Role> findAll();

    public Role findById(Long id);

    public Role findByName(String name);

    public Role save(Role role);
}
