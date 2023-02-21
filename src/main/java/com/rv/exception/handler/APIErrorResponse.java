package com.rv.exception.handler;

import org.springframework.http.HttpStatus;

public class APIErrorResponse {
  private String message;
  private HttpStatus status;

  public APIErrorResponse(String message,HttpStatus status) {
    super();
    this.message = message;
    this.status = status;
  }

  public String getMessage() {
    return message;
  }


  public HttpStatus getStatus() {
	return status;
  }

}