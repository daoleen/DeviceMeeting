package com.daoleen.devicemeeting.web.controller.admin;

import com.daoleen.devicemeeting.web.service.RoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;

/**
 * Created by alex on 17.6.14.
 */

@Controller
@RequestMapping("/admin/role")
public class RoleController {

    private static final Logger logger = LoggerFactory.getLogger(RoleController.class);

    @Resource(name = "roleService")
    private RoleService roleService;

    @RequestMapping(method = RequestMethod.GET)
    public String index() {
        logger.debug("roles count: {}", roleService.findAll().size());
        return "admin/role/index";
    }

}
