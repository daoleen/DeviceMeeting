package com.daoleen.devicemeeting.web.controller;

import java.time.LocalDateTime;
import java.util.Locale;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.daoleen.devicemeeting.web.domain.Room;
import com.daoleen.devicemeeting.web.domain.User;
import com.daoleen.devicemeeting.web.infrastructure.RoomPageParameter;
import com.daoleen.devicemeeting.web.service.InviteService;
import com.daoleen.devicemeeting.web.service.RoomService;
import com.daoleen.devicemeeting.web.viewmodel.RoomPage;

@Controller
@RequestMapping("/room")
@Secured({"ROLE_USER", "ROLE_ADMIN"})
public class RoomController {
	private static final Logger logger = LoggerFactory.getLogger(RoomController.class);
	
	@Resource(name = "roomService")
	private RoomService roomService;
	
	@Resource(name = "inviteService")
	private InviteService inviteService;
	
	@Autowired
	private MessageSource messageSource;
	
	@PersistenceContext
	private EntityManager em;

	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public String create(Model model) {
		logger.debug("Creating a new room");
		logger.debug("The example of LocalDateTime: {}", LocalDateTime.now());
		model.addAttribute("room", new Room());
		return "room/create";
	}
	
	@RequestMapping(value = "/new", method = RequestMethod.POST)
	public String process(Model model, @Valid Room room, BindingResult bindingResult,
			RedirectAttributes redirectAttrs, Locale locale) {
		
		if(!bindingResult.hasErrors()) {
			User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//			room.setOwnerId(user.getId());
			user = em.merge(user);	// The user can be detached
			room.setOwner(user);
			room = roomService.save(room);
			redirectAttrs.addFlashAttribute("system_notice", messageSource.getMessage("room.create.notice.success", null, locale));
			logger.debug("The room has been successfully created");
			return String.format("redirect:/room/details/%d", room.getId());
		}
		
		model.addAttribute("room", room);
		redirectAttrs.addFlashAttribute("system_notice", messageSource.getMessage("room.create.notice.fail", null, locale));
		logger.debug("An errors has been detected for room-form");
		return "room/create";
	}
	
	
	@Secured({"ROLE_ANONYMOUS", "ROLE_USER", "ROLE_ADMIN"})
	@RequestMapping(method = RequestMethod.GET)
	public String list(Model model, @RoomPageParameter RoomPage roomPage) {
		Page<Room> rooms = roomService.findAllPageable(roomPage);
		model.addAttribute("rooms", rooms);
		return "room/list";
	}
	
	@Secured({"ROLE_ANONYMOUS", "ROLE_USER", "ROLE_ADMIN"})
	@RequestMapping(value = "/search", method = RequestMethod.GET, params = "keywords")
	public String search(Model model, @RoomPageParameter RoomPage roomPage, String keywords) {
		Page<Room> rooms = roomService.searchByName(keywords, roomPage);
		logger.debug("Total received rooms is: {}", rooms.getNumberOfElements());
		model.addAttribute("rooms", rooms);
		model.addAttribute("keywords", keywords);
		return "room/search";
	}
	
	@Secured({"ROLE_ANONYMOUS", "ROLE_USER", "ROLE_ADMIN"})
	@RequestMapping(value = "/details/{id}", method = RequestMethod.GET)
	public String details(Model model, @PathVariable("id") Long id) {
		User user = null;
		Room room = roomService.findById(id);
		SecurityContext context = SecurityContextHolder.getContext();
		Authentication auth = context.getAuthentication();
		//boolean authenticated = auth.getAuthorities().	// PROBLEM: ROLE_ANONYMOUS , authenticated = true
		//boolean authenticated = auth.isAuthenticated();
		
		logger.debug("Authorities are:");
		auth.getAuthorities().parallelStream().forEach(a -> logger.debug("\t{}", a.getAuthority()));
		
//		if(authenticated) {
//			logger.debug("Authentication is: {}", auth.getClass().equals(AnonymousAuthenticationToken.class));
//			user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//		}
		
		user = (auth.getClass().equals(AnonymousAuthenticationToken.class)) ? null 
				: (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal(); 
		
		boolean hasInvite = (user != null) && (inviteService.findOne(user.getId(), id) != null);
		model.addAttribute("room", room);
		model.addAttribute("hasInvite", hasInvite);
		return "room/details";
	}
}
