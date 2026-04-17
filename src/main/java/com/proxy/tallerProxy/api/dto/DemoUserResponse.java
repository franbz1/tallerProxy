package com.proxy.tallerProxy.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Demo user fixture for UI selector (taller / local only).")
public class DemoUserResponse {

	@Schema(example = "demo-free")
	private String externalId;

	@Schema(example = "FREE")
	private String plan;

	@Schema(description = "null when no practical RPM cap (ENTERPRISE)")
	private Integer requestsPerMinute;

	@Schema(description = "null when unlimited (ENTERPRISE)")
	private Long monthlyTokenLimit;

	@Schema(description = "Short label for dropdowns")
	private String displayLabel;

	public DemoUserResponse() {
	}

	public DemoUserResponse(String externalId, String plan, Integer requestsPerMinute, Long monthlyTokenLimit,
			String displayLabel) {
		this.externalId = externalId;
		this.plan = plan;
		this.requestsPerMinute = requestsPerMinute;
		this.monthlyTokenLimit = monthlyTokenLimit;
		this.displayLabel = displayLabel;
	}

	public String getExternalId() {
		return externalId;
	}

	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}

	public String getPlan() {
		return plan;
	}

	public void setPlan(String plan) {
		this.plan = plan;
	}

	public Integer getRequestsPerMinute() {
		return requestsPerMinute;
	}

	public void setRequestsPerMinute(Integer requestsPerMinute) {
		this.requestsPerMinute = requestsPerMinute;
	}

	public Long getMonthlyTokenLimit() {
		return monthlyTokenLimit;
	}

	public void setMonthlyTokenLimit(Long monthlyTokenLimit) {
		this.monthlyTokenLimit = monthlyTokenLimit;
	}

	public String getDisplayLabel() {
		return displayLabel;
	}

	public void setDisplayLabel(String displayLabel) {
		this.displayLabel = displayLabel;
	}
}
