package com.daoleen.devicemeeting.web.controller.test;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RequestMapping(value = "/test")
public class UrlParametersTestController {
	
	public String noRequestMapping() {
		return "views/empty";
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/route/mapping/{param}/{value}")
	public String hasRequestMapping() {
		return "views/route";
	}
	
	@RequestMapping(value = "/form/submit", method = RequestMethod.POST)
	public String hasPostRequestMapping() {
		return "views/redirect";
	}
}
