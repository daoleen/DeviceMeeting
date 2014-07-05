package com.daoleen.devicemeeting.web.controller;

import com.daoleen.devicemeeting.web.domain.Role;
import com.daoleen.devicemeeting.web.domain.User;
import com.daoleen.devicemeeting.web.infrastructure.MyAuthenticationToken;
import com.daoleen.devicemeeting.web.service.RoleService;
import com.daoleen.devicemeeting.web.service.UserService;
import com.daoleen.devicemeeting.web.viewmodel.Account;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by alex on 18.6.14.
 */

@Controller
@Secured("ROLE_ANONYMOUS")
@RequestMapping(value = "/account")
public class AccountController {
    private static final Logger logger = LoggerFactory.getLogger(AccountController.class);

    @Resource(name = "userService")
    private UserService userService;

    @Resource(name = "roleService")
    private RoleService roleService;

    @Resource(name = "messageSource")
    private MessageSource messageSource;

    @Resource(name = "passwordEncoder")
    private PasswordEncoder passwordEncoder;


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

        if (!passwordEncoder.matches(password, user.getPassword())) {
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
        model.addAttribute("user", user);
        return "account/details";
    }
}
