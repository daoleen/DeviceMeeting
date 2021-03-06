package com.daoleen.devicemeeting.web.service.impl;

import com.daoleen.devicemeeting.web.domain.Role;
import com.daoleen.devicemeeting.web.repository.RoleRepository;
import com.daoleen.devicemeeting.web.service.RoleService;
import com.google.common.collect.Lists;
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
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional(readOnly = true)
    public List<Role> findAll() {
        return Lists.newArrayList(roleRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public Role findById(Long id) {
        return roleRepository.findOne(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Role findByName(String name) {
        //noinspection JpaQlInspection
        return entityManager.createQuery("select r from Role r where r.authority = :authority", Role.class)
                .setParameter("authority", name)
                .getSingleResult();
    }

    @Override
    public Role save(Role role) {
        return roleRepository.save(role);
    }
}
