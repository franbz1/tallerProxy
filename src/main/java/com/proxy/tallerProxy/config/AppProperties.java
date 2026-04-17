package com.proxy.tallerProxy.config;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app")
public class AppProperties {

	private String timezone = "UTC";

	private int tokenEstimationFixedOutputTokens = 100;

	/**
	 * Comma-separated browser origins allowed for CORS (e.g. your Next dev server).
	 * Override with env {@code APP_CORS_ALLOWED_ORIGINS} (see application.properties).
	 */
	private String corsAllowedOrigins = "http://localhost:3000,http://127.0.0.1:3000";

	public String getTimezone() {
		return timezone;
	}

	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}

	public int getTokenEstimationFixedOutputTokens() {
		return tokenEstimationFixedOutputTokens;
	}

	public void setTokenEstimationFixedOutputTokens(int tokenEstimationFixedOutputTokens) {
		this.tokenEstimationFixedOutputTokens = tokenEstimationFixedOutputTokens;
	}

	public String getCorsAllowedOrigins() {
		return corsAllowedOrigins;
	}

	public void setCorsAllowedOrigins(String corsAllowedOrigins) {
		this.corsAllowedOrigins = corsAllowedOrigins;
	}

	public List<String> getCorsAllowedOriginsList() {
		return Arrays.stream(corsAllowedOrigins.split(","))
				.map(String::trim)
				.filter(s -> !s.isEmpty())
				.collect(Collectors.toList());
	}
}
