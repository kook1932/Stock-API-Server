package it.numble.toss.exception.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class CommonExceptionHandler {

	@ExceptionHandler(CommonException.class)
	public ResponseEntity<Map<String, String>> commonExceptionHandler(CommonException e) {
		HttpHeaders httpHeaders = new HttpHeaders();
		HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

		Map<String, String> responseBody = new HashMap<>();
		responseBody.put("error type", e.getHttpStatusType());
		responseBody.put("code", Integer.toString(e.getHttpStatusCode()));
		responseBody.put("message", e.getMessage());

		return new ResponseEntity<>(responseBody, httpHeaders, httpStatus);
	}
}
