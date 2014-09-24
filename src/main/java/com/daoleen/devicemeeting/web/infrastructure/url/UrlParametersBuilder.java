package com.daoleen.devicemeeting.web.infrastructure.url;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.hibernate.internal.util.StringHelper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

public class UrlParametersBuilder {
	private Map<String, String> paramsCollection = new HashMap<String, String>();
	private final String controllersPackage;

	public UrlParametersBuilder(String controllersPackage) {
		this.controllersPackage = controllersPackage;
	}

	public UrlParametersBuilder add(String paramName, String paramValue) {
		paramsCollection.put(paramName, paramValue);
		return this;
	}

	public String get(String controllerName, String actionName)
			throws SecurityException, ClassNotFoundException {
		return getRouteAndParamsByRequestMethod(controllerName, actionName, RequestMethod.GET);
	}
	
	public String post(String controllerName, String actionName) 
			throws SecurityException, ClassNotFoundException {
		return getRouteAndParamsByRequestMethod(controllerName, actionName, RequestMethod.POST);
	}
	
	public String getRouteAndParamsByRequestMethod(String controllerName, String actionName, RequestMethod requestMethod) 
			throws SecurityException, ClassNotFoundException {
		
		String classname = getClassname(controllerName);
		String route = getRequestMappingValueForClass(classname).concat(getRequestMappingValue(classname, actionName, requestMethod));		
		if(paramsCollection.isEmpty()) {
			return route;
		}
		
		StringBuilder builder = new StringBuilder(route);
		StringBuilder paramsBuilder = new StringBuilder();
		
		paramsCollection.forEach(
			(String k, String v) -> {
				if(StringHelper.isNotEmpty(v)) {
					int index = builder.indexOf(String.format("{%s}", k));
					if(index != -1) {
						builder.replace(index, index + k.length()+2, v);
					}
					else {
						paramsBuilder.append('&').append(k).append('=').append(v);
					}
				}
		});
		
		if(paramsBuilder.length() > 0) {
			paramsBuilder.replace(0, 1, "?");	// replace first symbol from '&' to '?'
			return builder.append(paramsBuilder).toString();
		}
		
		return builder.toString();
	}
	
	
	
	private String getRequestMappingValue(String classname, String actionName, RequestMethod requestMethod) throws SecurityException, ClassNotFoundException {
		Method[] methods = Class.forName(classname).getMethods();
		if (methods != null) {
			for (Method m : methods) {
				if (m.getName().equals(actionName)) {
					RequestMapping requestMapping = m.getAnnotation(RequestMapping.class);
					if(requestMapping != null) {
						RequestMethod[] requestMethods = requestMapping.method();
						for (RequestMethod annotationRequestMethod : requestMethods) {
							if (annotationRequestMethod.equals(requestMethod)) {
								String[] values = requestMapping.value();
								if (values.length > 0) {
									return values[0];
								}
							}
						}
					}
				}
			}
		}
		return "/";
	}
	
	private String getRequestMappingValueForClass(String classname) throws ClassNotFoundException {
		RequestMapping mapping = Class.forName(classname).getAnnotation(RequestMapping.class);
		if(mapping == null) {
			return "";
		}
		
		if(mapping.value().length > 0) {
			return mapping.value()[0];
		}
		
		return "";
	}
	
	private String getClassname(String controllerName) {
		return String.format("%s.%s%s", controllersPackage, controllerName, "Controller");
	}
}
