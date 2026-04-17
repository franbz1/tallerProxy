package com.proxy.tallerProxy.domain;

/**
 * Subscription plans. ENTERPRISE has no effective token cap and no practical rate limit.
 */
public enum Plan {

	FREE(10, 50_000L),
	PRO(60, 500_000L),
	/** Low RPM for quick rate-limit demos (same monthly cap as FREE). */
	DEMO_THROTTLED(2, 50_000L),
	ENTERPRISE(Integer.MAX_VALUE, null);

	private final int requestsPerMinute;
	private final Long monthlyTokenLimit;

	Plan(int requestsPerMinute, Long monthlyTokenLimit) {
		this.requestsPerMinute = requestsPerMinute;
		this.monthlyTokenLimit = monthlyTokenLimit;
	}

	public int getRequestsPerMinute() {
		return requestsPerMinute;
	}

	/**
	 * @return null when monthly token quota is unlimited (ENTERPRISE).
	 */
	public Long getMonthlyTokenLimit() {
		return monthlyTokenLimit;
	}

	public boolean isUnlimitedTokens() {
		return monthlyTokenLimit == null;
	}
}
