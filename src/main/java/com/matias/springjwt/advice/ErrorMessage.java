package com.matias.springjwt.advice;

import java.util.Date;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ErrorMessage {

	private int statusCode;
	private Date timestamp;
	private String message;
	private String description;

}