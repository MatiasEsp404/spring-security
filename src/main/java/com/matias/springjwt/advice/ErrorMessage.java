package com.matias.springjwt.advice;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Builder
public class ErrorMessage {

	private int statusCode;
	private Date timestamp;
	private String message;
	private String description;

}