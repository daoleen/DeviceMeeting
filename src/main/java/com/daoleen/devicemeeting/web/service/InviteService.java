package com.daoleen.devicemeeting.web.service;

import java.util.List;

import com.daoleen.devicemeeting.web.domain.Invite;

public interface InviteService {
	public List<Invite> findAll();
	public Invite findById(Long id);
	public Invite save(Invite invate);
	public void delete(Invite invate);
	public List<Invite> findByRoomId(Long roomId);
	public List<Invite> findByUserId(Long userId);
	public Invite findOne(Long userId, Long roomId);
	public int findUnreadInvitationsCount(Long userId);
}