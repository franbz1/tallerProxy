package com.proxy.tallerProxy.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Mock payment payload for FREE → PRO upgrade.")
public class UpgradeRequest {

	@Schema(description = "When false, simulates a declined payment", example = "true")
	private boolean simulateSuccess = true;

	@Schema(example = "mock-card")
	private String paymentMethod = "mock";

	public boolean isSimulateSuccess() {
		return simulateSuccess;
	}

	public void setSimulateSuccess(boolean simulateSuccess) {
		this.simulateSuccess = simulateSuccess;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
}
