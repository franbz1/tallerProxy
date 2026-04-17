package com.proxy.tallerProxy.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Standard error payload for API failures.")
public class ApiErrorResponse {

	@Schema(description = "Stable machine-readable code", example = "RATE_LIMIT")
	private String code;

	@Schema(description = "Human-readable message")
	private String message;

	@Schema(description = "Seconds until the client may retry (rate limit only)", nullable = true)
	private Long retryAfterSeconds;

	public ApiErrorResponse() {
	}

	public ApiErrorResponse(String code, String message, Long retryAfterSeconds) {
		this.code = code;
		this.message = message;
		this.retryAfterSeconds = retryAfterSeconds;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Long getRetryAfterSeconds() {
		return retryAfterSeconds;
	}

	public void setRetryAfterSeconds(Long retryAfterSeconds) {
		this.retryAfterSeconds = retryAfterSeconds;
	}
}
