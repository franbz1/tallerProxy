package com.proxy.tallerProxy.api.dto;

import java.time.Instant;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Current sliding window rate limit snapshot (per calendar minute).")
public class RateLimitInfo {

	@Schema(description = "Requests counted in the current minute window")
	private int requestsUsedThisWindow;

	@Schema(description = "Max requests per minute for the plan; null when unlimited (ENTERPRISE)")
	private Integer requestsLimitPerMinute;

	@Schema(description = "UTC instant when the current minute window ends and the counter resets")
	private Instant rateLimitResetAt;

	public RateLimitInfo() {
	}

	public RateLimitInfo(int requestsUsedThisWindow, Integer requestsLimitPerMinute, Instant rateLimitResetAt) {
		this.requestsUsedThisWindow = requestsUsedThisWindow;
		this.requestsLimitPerMinute = requestsLimitPerMinute;
		this.rateLimitResetAt = rateLimitResetAt;
	}

	public int getRequestsUsedThisWindow() {
		return requestsUsedThisWindow;
	}

	public void setRequestsUsedThisWindow(int requestsUsedThisWindow) {
		this.requestsUsedThisWindow = requestsUsedThisWindow;
	}

	public Integer getRequestsLimitPerMinute() {
		return requestsLimitPerMinute;
	}

	public void setRequestsLimitPerMinute(Integer requestsLimitPerMinute) {
		this.requestsLimitPerMinute = requestsLimitPerMinute;
	}

	public Instant getRateLimitResetAt() {
		return rateLimitResetAt;
	}

	public void setRateLimitResetAt(Instant rateLimitResetAt) {
		this.rateLimitResetAt = rateLimitResetAt;
	}
}
