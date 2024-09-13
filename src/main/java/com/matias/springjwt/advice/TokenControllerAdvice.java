package com.matias.springjwt.advice;

import com.matias.springjwt.exception.TokenRefreshException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@RestControllerAdvice
public class TokenControllerAdvice {

    @ExceptionHandler(value = TokenRefreshException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorMessage handleTokenRefreshException(TokenRefreshException ex, WebRequest request) {
    	ErrorMessage msg = new ErrorMessage();
    	msg.setStatusCode(HttpStatus.FORBIDDEN.value());
    	msg.setTimestamp(new Date());
    	msg.setMessage(ex.getMessage());
    	msg.setDescription(request.getDescription(false));
    	return msg;
    }
    
}