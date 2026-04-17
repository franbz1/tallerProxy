package com.proxy.tallerProxy.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Result of a plan upgrade attempt.")
public class UpgradeResponse {

	@Schema(example = "PRO")
	private String plan;

	@Schema(description = "Echo of the mock payment success flag")
	private boolean paymentAccepted;

	public UpgradeResponse() {
	}

	public UpgradeResponse(String plan, boolean paymentAccepted) {
		this.plan = plan;
		this.paymentAccepted = paymentAccepted;
	}

	public String getPlan() {
		return plan;
	}

	public void setPlan(String plan) {
		this.plan = plan;
	}

	public boolean isPaymentAccepted() {
		return paymentAccepted;
	}

	public void setPaymentAccepted(boolean paymentAccepted) {
		this.paymentAccepted = paymentAccepted;
	}
}
