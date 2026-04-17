package com.proxy.tallerProxy.service;

import java.time.Instant;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Service;

import com.proxy.tallerProxy.api.dto.RateLimitInfo;
import com.proxy.tallerProxy.api.exception.RateLimitExceededException;
import com.proxy.tallerProxy.domain.AppUser;
import com.proxy.tallerProxy.domain.Plan;

@Service
public class RateLimitProxyService {

	private final ConcurrentHashMap<String, AtomicInteger> requestsThisMinute = new ConcurrentHashMap<>();

	private final TimeProvider timeProvider;

	public RateLimitProxyService(TimeProvider timeProvider) {
		this.timeProvider = timeProvider;
	}

	/**
	 * Clears per-user counters at the start of each new minute (scheduled).
	 */
	public void resetMinuteWindow() {
		requestsThisMinute.clear();
	}

	public void validateAndIncrement(AppUser user) {
		if (user.getPlan() == Plan.ENTERPRISE) {
			return;
		}
		int limit = user.getPlan().getRequestsPerMinute();
		String key = user.getExternalId();
		AtomicInteger counter = requestsThisMinute.computeIfAbsent(key, k -> new AtomicInteger(0));
		int after = counter.incrementAndGet();
		if (after > limit) {
			counter.decrementAndGet();
			long retry = timeProvider.secondsToEndOfCurrentUtcMinute();
			throw new RateLimitExceededException(retry,
					"Rate limit exceeded for plan " + user.getPlan() + ". Try again after the current minute window.");
		}
	}

	public RateLimitInfo snapshot(AppUser user) {
		Instant resetAt = timeProvider.endOfCurrentUtcMinute();
		if (user.getPlan() == Plan.ENTERPRISE) {
			return new RateLimitInfo(0, null, resetAt);
		}
		AtomicInteger c = requestsThisMinute.get(user.getExternalId());
		int used = c == null ? 0 : c.get();
		Integer lim = user.getPlan().getRequestsPerMinute();
		return new RateLimitInfo(used, lim, resetAt);
	}
}
