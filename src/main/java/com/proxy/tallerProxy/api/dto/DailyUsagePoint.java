package com.proxy.tallerProxy.api.dto;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Aggregated token usage for a single day.")
public class DailyUsagePoint {

	private LocalDate date;

	private long tokensUsed;

	public DailyUsagePoint() {
	}

	public DailyUsagePoint(LocalDate date, long tokensUsed) {
		this.date = date;
		this.tokensUsed = tokensUsed;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public long getTokensUsed() {
		return tokensUsed;
	}

	public void setTokensUsed(long tokensUsed) {
		this.tokensUsed = tokensUsed;
	}
}
