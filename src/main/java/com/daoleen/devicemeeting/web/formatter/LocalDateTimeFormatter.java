package com.daoleen.devicemeeting.web.formatter;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.format.Formatter;

public class LocalDateTimeFormatter implements Formatter<LocalDateTime> {

	@Autowired
	private MessageSource messageSource;
	
	@Override
	public String print(LocalDateTime date, Locale locale) {
		//String format = messageSource.getMessage("date.format", null, locale);	// for formatting from ResourceBundles
		//DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format, locale);
		DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL).withLocale(locale);
		return date.format(formatter);
	}

	@Override
	public LocalDateTime parse(String text, Locale locale) throws ParseException {
		return LocalDateTime.parse(text);
	}
}
