package com.proxy.tallerProxy.api.dto;

import java.time.Instant;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Quota and rate limit state for the authenticated user (via X-User-Id).")
public class QuotaStatusResponse {

	@Schema(example = "FREE")
	private String plan;

	private long tokensUsedThisMonth;

	@Schema(description = "Remaining tokens this month; null when unlimited")
	private Long tokensRemainingThisMonth;

	@Schema(description = "Monthly token cap for the plan; null when unlimited")
	private Long monthlyTokenLimit;

	@Schema(description = "First instant of the next billing month in the configured app timezone, expressed as UTC Instant")
	private Instant monthlyResetAt;

	private RateLimitInfo rateLimit;

	public QuotaStatusResponse() {
	}

	public String getPlan() {
		return plan;
	}

	public void setPlan(String plan) {
		this.plan = plan;
	}

	public long getTokensUsedThisMonth() {
		return tokensUsedThisMonth;
	}

	public void setTokensUsedThisMonth(long tokensUsedThisMonth) {
		this.tokensUsedThisMonth = tokensUsedThisMonth;
	}

	public Long getTokensRemainingThisMonth() {
		return tokensRemainingThisMonth;
	}

	public void setTokensRemainingThisMonth(Long tokensRemainingThisMonth) {
		this.tokensRemainingThisMonth = tokensRemainingThisMonth;
	}

	public Long getMonthlyTokenLimit() {
		return monthlyTokenLimit;
	}

	public void setMonthlyTokenLimit(Long monthlyTokenLimit) {
		this.monthlyTokenLimit = monthlyTokenLimit;
	}

	public Instant getMonthlyResetAt() {
		return monthlyResetAt;
	}

	public void setMonthlyResetAt(Instant monthlyResetAt) {
		this.monthlyResetAt = monthlyResetAt;
	}

	public RateLimitInfo getRateLimit() {
		return rateLimit;
	}

	public void setRateLimit(RateLimitInfo rateLimit) {
		this.rateLimit = rateLimit;
	}
}
