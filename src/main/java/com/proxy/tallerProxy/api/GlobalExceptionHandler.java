package com.proxy.tallerProxy.api;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.proxy.tallerProxy.api.dto.ApiErrorResponse;
import com.proxy.tallerProxy.api.exception.InvalidUpgradeException;
import com.proxy.tallerProxy.api.exception.QuotaExceededException;
import com.proxy.tallerProxy.api.exception.RateLimitExceededException;
import com.proxy.tallerProxy.api.exception.UserNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<ApiErrorResponse> handleIllegalArgument(IllegalArgumentException ex) {
		ApiErrorResponse body = new ApiErrorResponse("BAD_REQUEST", ex.getMessage(), null);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
	}

	@ExceptionHandler(RateLimitExceededException.class)
	public ResponseEntity<ApiErrorResponse> handleRateLimit(RateLimitExceededException ex) {
		long retry = ex.getRetryAfterSeconds();
		ApiErrorResponse body = new ApiErrorResponse("RATE_LIMIT", ex.getMessage(), retry);
		return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS)
				.header(HttpHeaders.RETRY_AFTER, Long.toString(retry))
				.body(body);
	}

	@ExceptionHandler(QuotaExceededException.class)
	public ResponseEntity<ApiErrorResponse> handleQuota(QuotaExceededException ex) {
		ApiErrorResponse body = new ApiErrorResponse("QUOTA_EXHAUSTED", ex.getMessage(), null);
		return ResponseEntity.status(HttpStatus.PAYMENT_REQUIRED).body(body);
	}

	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<ApiErrorResponse> handleNotFound(UserNotFoundException ex) {
		ApiErrorResponse body = new ApiErrorResponse("USER_NOT_FOUND", ex.getMessage(), null);
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
	}

	@ExceptionHandler(InvalidUpgradeException.class)
	public ResponseEntity<ApiErrorResponse> handleBadUpgrade(InvalidUpgradeException ex) {
		ApiErrorResponse body = new ApiErrorResponse("INVALID_UPGRADE", ex.getMessage(), null);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ApiErrorResponse> handleValidation(MethodArgumentNotValidException ex) {
		String msg = ex.getBindingResult().getFieldErrors().stream()
				.findFirst()
				.map(err -> err.getField() + ": " + err.getDefaultMessage())
				.orElse("Validation failed");
		ApiErrorResponse body = new ApiErrorResponse("VALIDATION_ERROR", msg, null);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
	}
}
