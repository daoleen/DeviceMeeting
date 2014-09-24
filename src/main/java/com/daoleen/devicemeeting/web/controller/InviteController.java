package com.daoleen.devicemeeting.web.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.daoleen.devicemeeting.web.domain.Invite;
import com.daoleen.devicemeeting.web.domain.User;
import com.daoleen.devicemeeting.web.service.InviteService;

@Controller
@RequestMapping("/invites")
public class InviteController {
	
	@Resource(name = "inviteService")
	private InviteService inviteService;
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@RequestMapping(method = RequestMethod.GET)
	public String list(Model model, Long roomId) {
		List<Invite> invites = inviteService.findByRoomId(roomId);
		model.addAttribute("invites", invites);
		
		User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		user = entityManager.merge(user);
		
		return "invite/list";
	}
	
}
