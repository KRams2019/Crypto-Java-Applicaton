package com.example.demo.apiResponse;

import org.springframework.http.HttpStatus;
/**
* This class defines the API Response format.
* @author M1056279
* @version 1.0
*
*/
public class ApiResponse {

	private String header;
	private boolean error;
	private Object body;
	private HttpStatus httpStatus;

	public ApiResponse() {
		super();
	}

	public ApiResponse(String header, boolean error, Object body, HttpStatus httpStatus) {
		super();
		this.header = header;
		this.error = error;
		this.body = body;
		this.httpStatus = httpStatus;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public boolean isError() {
		return error;
	}

	public void setError(boolean error) {
		this.error = error;
	}

	public Object getBody() {
		return body;
	}

	public void setBody(Object body) {
		this.body = body;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	public void setHttpStatus(HttpStatus httpStatus) {
		this.httpStatus = httpStatus;
	}

}
