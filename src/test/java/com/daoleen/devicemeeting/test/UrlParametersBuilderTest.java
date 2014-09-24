package com.daoleen.devicemeeting.test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import com.daoleen.devicemeeting.web.infrastructure.url.UrlParametersBuilder;

@RunWith(BlockJUnit4ClassRunner.class)
public class UrlParametersBuilderTest {
	
	private  ApplicationContext context;
	
	@Before
	public void init() {
		context = new GenericXmlApplicationContext("classpath:spring-context.xml");
	}
	
	@Test
	public void testWasInitUrlBuilder() {
		Assert.assertNotNull("The urlBuider is null", context.getBean("urlBuilder", UrlParametersBuilder.class));
	}
	
	@Test
	public void testEmptyRoutesAndParameters() throws SecurityException, ClassNotFoundException {
		UrlParametersBuilder builder = context.getBean("urlBuilder", UrlParametersBuilder.class);
		String expected = "/test/";
		String actual = builder.get("UrlParametersTest", "noRequestMapping");
		Assert.assertEquals("The return value must be / because RequestMapping is null and one has no parameters", expected, actual);
	}
	
	@Test
	public void testRouteOnly() throws SecurityException, ClassNotFoundException {
		UrlParametersBuilder builder = context.getBean("urlBuilder", UrlParametersBuilder.class);
		Assert.assertEquals("The routes are not equals", "/test/route/mapping/{param}/{value}", builder.get("UrlParametersTest", "hasRequestMapping"));
	}
	
	@Test
	public void testParamsOnly() throws SecurityException, ClassNotFoundException {
		UrlParametersBuilder builder = context.getBean("urlBuilder", UrlParametersBuilder.class);
		String actual = builder.add("id", "4").add("name", "myName").add("mask", "hidden").get("UrlParametersTest", "noRequestMapping");
		Assert.assertEquals("The parameters are not equals", "/test/?name=myName&id=4&mask=hidden", actual);
	}
	
	@Test
	public void testOneParam() throws SecurityException, ClassNotFoundException {
		UrlParametersBuilder builder = context.getBean("urlBuilder", UrlParametersBuilder.class);
		Assert.assertEquals("The params are not equals", "/test/?id=2", 
				builder.add("id", "2").get("UrlParametersTest", "noRequestMapping"));
	}
	
	@Test
	public void testRouteAndParams() throws SecurityException, ClassNotFoundException {
		UrlParametersBuilder builder = context.getBean("urlBuilder", UrlParametersBuilder.class);
		// RequestMapping is: /route/mapping/{param}/{value}
		String actual = builder.add("name", "id").add("digit", "7").get("UrlParametersTest", "hasRequestMapping");
		Assert.assertEquals("The entire routes are not equals", "/test/route/mapping/{param}/{value}?name=id&digit=7", actual);
	}
	
	@Test
	public void testPOSTRouteOnly() throws SecurityException, ClassNotFoundException {
		UrlParametersBuilder builder = context.getBean("urlBuilder", UrlParametersBuilder.class);
		Assert.assertEquals("The routes are not equals", "/test/form/submit", builder.post("UrlParametersTest", "hasPostRequestMapping"));
	}
	
	@Test
	public void testRouteParams() throws SecurityException, ClassNotFoundException {
		// RequestMapping is: /route/mapping/{param}/{value}
		UrlParametersBuilder builder = context.getBean("urlBuilder", UrlParametersBuilder.class);
		String actual = builder.add("param", "id").add("value", "7").get("UrlParametersTest", "hasRequestMapping");
		String expected = "/test/route/mapping/id/7";
		Assert.assertEquals("The routes are not equals", expected, actual);
	}
	
	@Test
	public void testRoutesAndParamsBoth() throws SecurityException, ClassNotFoundException {
		// RequestMapping is: /route/mapping/{param}/{value}
		UrlParametersBuilder builder = context.getBean("urlBuilder", UrlParametersBuilder.class);
		String actual = builder.add("param", "id")
							.add("value", "7")
							.add("sortOrder", "desc")
							.add("name", "myName")
							.get("UrlParametersTest", "hasRequestMapping");
		String expected = "/test/route/mapping/id/7?sortOrder=desc&name=myName";
		Assert.assertEquals("The routes are not equals", expected, actual);
	}
	
	@Test
	public void testNullableParameter() throws SecurityException, ClassNotFoundException {
		UrlParametersBuilder builder = context.getBean("urlBuilder", UrlParametersBuilder.class);
		String actual = builder.add("param", "id").add("nullparam", null).add("emptystring", "").get("UrlParametersTest", "noRequestMapping");
		String expected = "/test/?param=id";
		Assert.assertEquals("The routes are not equals", expected, actual);
	}
}
