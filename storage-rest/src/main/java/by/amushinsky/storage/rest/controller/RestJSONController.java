package by.amushinsky.storage.rest.controller;

import static org.springframework.http.HttpStatus.EXPECTATION_FAILED;
import static org.springframework.http.HttpStatus.NOT_ACCEPTABLE;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.fasterxml.jackson.core.JsonProcessingException;

public abstract class RestJSONController {
	
	private static final String TYPE_ERROR_TEMPLATE = "argument ''{0}'' is not of type {1}";
	private  static final String UNKNOWN_ERROR = "UNKNOWN_ERROR";
	
	@ResponseStatus(NOT_ACCEPTABLE)
	@ExceptionHandler(JsonProcessingException.class)
	public List<String> wrongJson(JsonProcessingException exception) {
		return Collections.singletonList(exception.getMessage());
	}

	@ResponseStatus(NOT_ACCEPTABLE)
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public List<String> typeMismatch(MethodArgumentTypeMismatchException e) {
		String message = MessageFormat.format(TYPE_ERROR_TEMPLATE, e.getValue(), e.getRequiredType());
		return Collections.singletonList(message);
	}

	@ResponseStatus(EXPECTATION_FAILED)
	public List<String> unknownException(Exception exception) {
		return Collections.singletonList(UNKNOWN_ERROR);
	}

	public static class Error {

		public static final String UNKNOWN_ERROR = "UNKNOWN_ERROR";

		private int code;
		private String message;

		public Error(int code, String message) {
			this.code = code;
			this.message = message;
		}

		public int getCode() {
			return code;
		}

		public String getMessage() {
			return message;
		}
	}
}
