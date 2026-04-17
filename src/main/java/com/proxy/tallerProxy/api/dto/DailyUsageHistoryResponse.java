package com.proxy.tallerProxy.api.dto;

import java.util.ArrayList;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Last 7 calendar days of daily token usage (including zeros).")
public class DailyUsageHistoryResponse {

	private List<DailyUsagePoint> days = new ArrayList<>();

	public List<DailyUsagePoint> getDays() {
		return days;
	}

	public void setDays(List<DailyUsagePoint> days) {
		this.days = days;
	}
}
