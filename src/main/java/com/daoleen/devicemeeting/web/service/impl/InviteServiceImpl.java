package com.daoleen.devicemeeting.web.service.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.daoleen.devicemeeting.web.domain.Invite;
import com.daoleen.devicemeeting.web.repository.InviteRepository;
import com.daoleen.devicemeeting.web.service.InviteService;
import com.google.common.collect.Lists;

@Repository
@Transactional
public class InviteServiceImpl implements InviteService {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	private InviteRepository inviteRepository;
	
	@Override
	@Transactional(readOnly = true)
	public List<Invite> findAll() {
		return Lists.newArrayList(inviteRepository.findAll());
	}

	@Override
	@Transactional(readOnly = true)
	public Invite findById(Long id) {
		return inviteRepository.findOne(id);
	}

	@Override
	public Invite save(Invite invate) {
		return inviteRepository.save(invate);
	}

	@Override
	public void delete(Invite invate) {
		inviteRepository.delete(invate);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Invite> findByRoomId(Long roomId) {
		return entityManager.createQuery("select i from Invite i where i.room.id = :roomId", Invite.class)
				.setParameter("roomId", roomId)
				.getResultList();
	}

	@Override
	@Transactional(readOnly = true)
	public List<Invite> findByUserId(Long userId) {
		return entityManager.createQuery("select i from Invite i where i.user.id = :userId", Invite.class)
				.setParameter("userId", userId)
				.getResultList();
	}

	@Override
	@Transactional(readOnly = true)
	public Invite findOne(Long userId, Long roomId) {
		try {
		return entityManager.createQuery("select i from Invite i where i.user.id = :userId and i.room.id = :roomId", Invite.class)
				.setParameter("userId", userId)
				.setParameter("roomId", roomId)
				.getSingleResult();
		}
		catch(NoResultException noResult) {
			return null;
		}
	}
}