package com.daoleen.devicemeeting.web.webservice;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.daoleen.devicemeeting.web.domain.User;
import com.daoleen.devicemeeting.web.service.InviteService;

@RestController
@RequestMapping("/api/account")
public class AccountService {
	private final static Logger logger = LoggerFactory.getLogger(AccountService.class);
	
	@PersistenceContext
	private EntityManager em;
	
	@Resource(name = "inviteService")
	private InviteService inviteService;
	
	@RequestMapping(value = "/rooms-count", method = RequestMethod.GET)
	public String getMyRoomsCount() {
		logger.debug("The getMyRoomsCount() method has been invoked at RestController");
		User user = em.merge((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
		return String.valueOf(user.getRooms().size());
	}
	
	@RequestMapping(value = "/unread-invites", method = RequestMethod.GET)
	public String getUnreadInvitationsToMe() {
		logger.debug("The getUnreadInvitationsToMe() method has been invoked");
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return String.valueOf(inviteService.findUnreadInvitationsCount(user.getId()));
	}
}
