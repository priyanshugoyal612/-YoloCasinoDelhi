package com.yolo.casino.delhi.exceptions;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import static com.yolo.casino.delhi.constant.YoloConstant.VALIDATION_FAILED;

import com.yolo.casino.delhi.model.ApiErrorResponse;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<ApiErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex,
			WebRequest request) {
		List<String> errors = ex.getBindingResult().getAllErrors().stream().map(error -> {
			if (error instanceof FieldError) {
				return ((FieldError) error).getField() + ": " + error.getDefaultMessage();
			} else {
				return error.getObjectName() + ": " + error.getDefaultMessage();
			}
		}).collect(Collectors.toList());

		ApiErrorResponse apiErrorResponse = new ApiErrorResponse(HttpStatus.BAD_REQUEST.value(), VALIDATION_FAILED,
				errors, request.getDescription(false).substring(4));

		return new ResponseEntity<>(apiErrorResponse, HttpStatus.BAD_REQUEST);
	}
}
