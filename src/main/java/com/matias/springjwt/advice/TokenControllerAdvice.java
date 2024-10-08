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
    public ErrorMessage handleTokenRefreshException(TokenRefreshException exception, WebRequest request) {
    	return ErrorMessage.builder()
    			.statusCode(HttpStatus.FORBIDDEN.value())
    			.timestamp(new Date())
    			.message(exception.getMessage())
    			.description(request.getDescription(false))
    			.build();
    }
    
}