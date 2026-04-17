package com.proxy.tallerProxy.service;

import org.springframework.stereotype.Component;

import com.proxy.tallerProxy.config.AppProperties;

@Component
public class TokenEstimator {

	private final AppProperties appProperties;

	public TokenEstimator(AppProperties appProperties) {
		this.appProperties = appProperties;
	}

	/**
	 * Documented convention aligned with OpenAI-style heuristics: ~4 characters per token.
	 */
	public long estimatePromptTokens(String prompt) {
		if (prompt == null || prompt.isEmpty()) {
			return 1L;
		}
		return Math.max(1L, (long) Math.ceil(prompt.length() / 4.0));
	}

	public long fixedOutputEstimate() {
		return appProperties.getTokenEstimationFixedOutputTokens();
	}

	public long totalChargeEstimate(String prompt) {
		return estimatePromptTokens(prompt) + fixedOutputEstimate();
	}
}
