package com.proxy.tallerProxy.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Mock AI output plus billing and quota snapshots.")
public class GenerationResponse {

	@Schema(description = "Generated mock text")
	private String text;

	@Schema(description = "Total tokens charged for this call (input estimate + fixed output estimate)")
	private long tokensCharged;

	@Schema(description = "Input-side estimate: ceil(len(prompt)/4)")
	private long estimatedInputTokens;

	@Schema(description = "Output-side estimate used for billing (server constant)")
	private long estimatedOutputTokens;

	private QuotaStatusResponse quotaStatus;

	public GenerationResponse() {
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public long getTokensCharged() {
		return tokensCharged;
	}

	public void setTokensCharged(long tokensCharged) {
		this.tokensCharged = tokensCharged;
	}

	public long getEstimatedInputTokens() {
		return estimatedInputTokens;
	}

	public void setEstimatedInputTokens(long estimatedInputTokens) {
		this.estimatedInputTokens = estimatedInputTokens;
	}

	public long getEstimatedOutputTokens() {
		return estimatedOutputTokens;
	}

	public void setEstimatedOutputTokens(long estimatedOutputTokens) {
		this.estimatedOutputTokens = estimatedOutputTokens;
	}

	public QuotaStatusResponse getQuotaStatus() {
		return quotaStatus;
	}

	public void setQuotaStatus(QuotaStatusResponse quotaStatus) {
		this.quotaStatus = quotaStatus;
	}
}
