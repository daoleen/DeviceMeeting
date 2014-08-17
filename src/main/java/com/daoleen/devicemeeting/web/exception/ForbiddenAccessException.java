package com.daoleen.devicemeeting.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "You don't have permissions to access this page")
public class ForbiddenAccessException extends RuntimeException {
}