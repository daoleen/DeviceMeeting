package com.daoleen.devicemeeting.web.controller;

import com.daoleen.devicemeeting.web.domain.Invite;
import com.daoleen.devicemeeting.web.domain.Role;
import com.daoleen.devicemeeting.web.domain.Room;
import com.daoleen.devicemeeting.web.domain.User;
import com.daoleen.devicemeeting.web.domain.UserDetails;
import com.daoleen.devicemeeting.web.exception.ForbiddenAccessException;
import com.daoleen.devicemeeting.web.infrastructure.MyAuthenticationToken;
import com.daoleen.devicemeeting.web.service.RoleService;
import com.daoleen.devicemeeting.web.service.UserDetailsService;
import com.daoleen.devicemeeting.web.service.UserService;
import com.daoleen.devicemeeting.web.viewmodel.Account;

import org.apache.bval.constraints.Email;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContext;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

/**
 * Created by alex on 18.6.14.
 */

@Controller
@Secured("ROLE_ANONYMOUS")
@RequestMapping(value = "/account")
public class AccountController {
    private static final Logger logger = LoggerFactory.getLogger(AccountController.class);
    private final int AccountConferencesDetailsCount = 5;

    @Resource(name = "userService")
    private UserService userService;

    @Resource(name = "roleService")
    private RoleService roleService;

    @Resource(name = "userDetailsService")
    private UserDetailsService userDetailsService;

    @Resource(name = "messageSource")
    private MessageSource messageSource;

    @Resource(name = "passwordEncoder")
    private PasswordEncoder passwordEncoder;
    
    @PersistenceContext
    private EntityManager em;


    @RequestMapping(value = "/signin", method = RequestMethod.GET)
    public String signin(Model model,
                         @RequestParam(required = false, value = "fail", defaultValue = "false") boolean fail) {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();

        logger.debug("Authentication is: {}", authentication.getPrincipal());
        model.addAttribute("fail", fail);
        return "account/signin";
    }


    @RequestMapping(value = "/signin", method = RequestMethod.POST, params = {"email", "password"})
    public String signin(@RequestParam("email") String email, @RequestParam("password") String password,
                         @RequestParam(value = "remember_me", required = false, defaultValue = "false") boolean remember_me,
                         RedirectAttributes redirectAttributes, Locale locale) {
        logger.debug("Retrieved params: {} and {}", email, password);
        SecurityContext securityContext = SecurityContextHolder.getContext();
        User user = userService.findByEmail(email);

        if (user == null || !passwordEncoder.matches(password, user.getPassword())) {
            logger.debug("Passwords does not match!");
            redirectAttributes.addFlashAttribute("system_notice",
                    messageSource.getMessage("label.notice.signin.error", null, locale)
            );
            return "redirect:/account/signin?fail";
        }

        //Authentication authentication = new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities());
        Authentication authentication = new MyAuthenticationToken(user);

        if(remember_me) {
            logger.warn("TODO: Remember_Me feature...");
            // TODO: remember me feature
        }

        securityContext.setAuthentication(authentication);
        logger.debug("User has been successfully done authentication test");

        return "redirect:/";
    }


    @RequestMapping(method = RequestMethod.GET, value = "/signup")
    public String signup(Model model) {
        model.addAttribute("account", new Account());
        return "account/signup";
    }


    @RequestMapping(method = RequestMethod.POST, value = "/signup")
    public String signup(Model model, @Valid Account account, BindingResult bindingResult,
                         RedirectAttributes redirectAttributes, Locale locale) {

        logger.debug("Registration:");
        logger.debug("BindingResultErrors are: {}", bindingResult.hasErrors());
        logger.debug("username is: {}", account.getEmail());
        logger.debug("password is: {}", account.getPasswords().getPassword());

        if (bindingResult.hasErrors()) {
            logger.debug(bindingResult.getAllErrors().get(0).toString());
            redirectAttributes.addFlashAttribute("system_notice",
                    messageSource.getMessage("label.notice.error", null, locale));
            model.addAttribute("account", account);
            return "account/signup";
        }

        String encodedPassword = passwordEncoder.encode(account.getPasswords().getPassword());
        User user = new User(account.getEmail(), encodedPassword);
        List<Role> roles = new ArrayList<Role>(1);

        roles.add(roleService.findByName("ROLE_USER"));
        user.setRoles(roles);
        user = userService.save(user);

        UserDetails userDetails = new UserDetails();
        userDetails.setUserId(user.getId());
        userDetailsService.save(userDetails);

        redirectAttributes.addFlashAttribute("system_notice",
                messageSource.getMessage("label.notice.saved", null, locale));
        return String.format("redirect:/account/%d", user.getId());
        // + UriUtils.encodePathSegment(user.getId().toString(), "utf-8");
    }


    @Secured("ROLE_USER")
    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public String details(Model model, @PathVariable("id") Long id) {
        logger.debug("Processing /account/{} page", id);
        User user = userService.findById(id);
        
        // My rooms
        List<Room> myRooms = user.getRooms().stream()
        		.sorted(new Comparator<Room>() {
					@Override
					public int compare(Room o1, Room o2) {
						return (int) (o2.getId() - o1.getId());
					}
				})
				.limit(AccountConferencesDetailsCount)
				.collect(Collectors.toList());
        
        
        // My last seen conferences
        List<Room> mySeenRooms = user.getInvite().stream()
        	.filter(i -> i.getDate().compareTo(LocalDateTime.now()) < 0)
        	.sorted(new Comparator<Invite>() {
        		@Override
        		public int compare(Invite o1, Invite o2) {
        			return o2.getDate().compareTo(o1.getDate());
        		}
			})
			.limit(AccountConferencesDetailsCount)
			.map(i -> i.getRoom())
			.collect(Collectors.toList());
        
        logger.debug("Last seen conferences count: {}", mySeenRooms.size());
        logger.debug("My own rooms count: {}", myRooms.size());
        model.addAttribute("user", user);
        model.addAttribute("myRooms", myRooms);
        model.addAttribute("mySeenRooms", mySeenRooms);
        return "account/details";
    }
    
    @Secured("ROLE_USER")
    @RequestMapping(method = RequestMethod.GET, value = "/edit/{id}")
    public String edit(Model model, @PathVariable("id") Long id) {
    	User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	
    	if(!user.getId().equals(id)) {
    		logger.debug("Forbidden access exception: currentUserId = {}, userId = {}", user.getId(), id);
    		throw new ForbiddenAccessException();
    	}
    	
    	logger.debug("Authentication is: " + user);
    	
    	model.addAttribute("userDetails", user.getUserDetails());
    	return "account/edit";
    }
    
    
    @Secured("ROLE_USER")
    @RequestMapping(method = RequestMethod.POST, value = "/edit")
    public String edit(Model model, @Valid UserDetails userDetails, BindingResult bindingResult,
    		RedirectAttributes redirectAttributes, Locale locale) {
    	User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	
    	if(!user.getId().equals(userDetails.getUserId())) {
    		logger.debug("Forbidden access exception: currentUserId = {}, userId = {}", user.getId(), userDetails.getUserId());
    		throw new ForbiddenAccessException();
    	}
    	
    	if(!bindingResult.hasErrors()) {
    		UserDetails updatedDetails = userDetailsService.findById(userDetails.getUserId());
    		updatedDetails.setFirstName(userDetails.getFirstName());
    		updatedDetails.setLastName(userDetails.getLastName());
    		updatedDetails.setAbout(userDetails.getAbout());
    		updatedDetails.setLinkedin(userDetails.getLinkedin());
    		updatedDetails.setSkype(userDetails.getSkype());
    		userDetailsService.save(updatedDetails);
    		
    		redirectAttributes.addFlashAttribute("system_notice", messageSource.getMessage("label.notice.saved", null, locale));
    		return String.format("redirect:/account/%d", userDetails.getUserId());
    	}
    	
    	redirectAttributes.addFlashAttribute("system_notice", messageSource.getMessage("label.notice.error", null, locale));
    	model.addAttribute("userDetails", userDetails);
    	return "account/edit";
    }
    
    
    @Secured("ROLE_USER")
    @RequestMapping("/my-rooms")
    public String myRooms(Model model) {
        User user = em.merge((User)SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        List<Room> myRooms = user.getRooms();
        
        model.addAttribute("rooms", myRooms);
    	return "account/myrooms";
    }
    
    @Secured("ROLE_USER")
    @RequestMapping("/my-last-seen-rooms")
    public String myLastSeenRooms(Model model) {
    	User user = em.merge((User)SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    	List<Room> mySeenRooms = user.getInvite().stream()
            	.filter(i -> i.getDate().compareTo(LocalDateTime.now()) < 0)
            	.sorted(new Comparator<Invite>() {
            		@Override
            		public int compare(Invite o1, Invite o2) {
            			return o2.getDate().compareTo(o1.getDate());
            		}
    			})
    			.map(i -> i.getRoom())
    			.collect(Collectors.toList());
    	
    	model.addAttribute("rooms", mySeenRooms);
    	return "account/myseenrooms";
    }
}
