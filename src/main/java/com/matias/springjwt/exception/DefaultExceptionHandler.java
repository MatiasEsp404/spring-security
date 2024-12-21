package com.matias.springjwt.exception;

import java.util.Collections;
import java.util.stream.Collectors;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.matias.springjwt.dto.response.ErrorResponse;

import javax.persistence.EntityNotFoundException;

@ControllerAdvice
public class DefaultExceptionHandler {

    @ExceptionHandler(value = EntityNotFoundException.class)
    protected ResponseEntity<ErrorResponse> handleEntityNotFound(EntityNotFoundException e) {
      ErrorResponse errorResponse = ErrorResponse.builder()
        .message("Entity not found.")
        .moreInfo(Collections.singletonList(e.getMessage()))
        .build();
      return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
  
    @ExceptionHandler(value = UsernameNotFoundException.class)
    protected ResponseEntity<ErrorResponse> handleUsernameNotFoundException(UsernameNotFoundException e) {
      ErrorResponse errorResponse = ErrorResponse.builder()
        .message("Username Not Found.")
        .moreInfo(Collections.singletonList(e.getMessage()))
        .build();
      return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
  
    @ExceptionHandler(value = InvalidCredentialsException.class)
    protected ResponseEntity<ErrorResponse> handleInvalidCredentialsException(InvalidCredentialsException e) {
      ErrorResponse errorResponse = ErrorResponse.builder()
        .message("The server cannot return a response due to invalid credentials.")
        .moreInfo(Collections.singletonList(e.getMessage()))
        .build();
      return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }
  
    @ExceptionHandler(value = UserAlreadyExistException.class)
    protected ResponseEntity<ErrorResponse> handleUserAlreadyExistException(UserAlreadyExistException e) {
      ErrorResponse errorResponse = ErrorResponse.builder()
        .message("User already exists.")
        .moreInfo(Collections.singletonList(e.getMessage()))
        .build();
      return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = TokenRefreshException.class)
    protected ResponseEntity<ErrorResponse> handleTokenRefreshException(TokenRefreshException e) {
      ErrorResponse errorResponse = ErrorResponse.builder()
        .message("Error trying to update token.")
        .moreInfo(Collections.singletonList(e.getMessage()))
        .build();
      return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
      ErrorResponse errorResponse = ErrorResponse.builder()
        .message("Invalid input data.")
        .moreInfo(e.getBindingResult().getFieldErrors().stream()
          .map(DefaultMessageSourceResolvable::getDefaultMessage)
          .collect(Collectors.toList()))
        .build();
      return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

}