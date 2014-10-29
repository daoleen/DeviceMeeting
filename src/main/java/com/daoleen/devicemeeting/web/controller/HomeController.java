package com.daoleen.devicemeeting.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by alex on 16.6.14.
 */

@Controller
@RequestMapping("/")
public class HomeController {
    private final static Logger logger = LoggerFactory.getLogger(HomeController.class);

    @RequestMapping(method = RequestMethod.GET, value = "/")
    public String index() {
        return "home/index";
    }

    @RequestMapping("/about")
    public String about() {
    	return "home/about";
    }
}
