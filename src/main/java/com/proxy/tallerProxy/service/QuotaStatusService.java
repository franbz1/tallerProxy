package com.proxy.tallerProxy.service;

import org.springframework.stereotype.Service;

import com.proxy.tallerProxy.api.dto.QuotaStatusResponse;
import com.proxy.tallerProxy.domain.AppUser;
import com.proxy.tallerProxy.domain.MonthlyUsage;
import com.proxy.tallerProxy.repository.MonthlyUsageRepository;

@Service
public class QuotaStatusService {

	private final MonthlyUsageRepository monthlyUsageRepository;
	private final RateLimitProxyService rateLimitProxyService;
	private final TimeProvider timeProvider;

	public QuotaStatusService(MonthlyUsageRepository monthlyUsageRepository,
			RateLimitProxyService rateLimitProxyService,
			TimeProvider timeProvider) {
		this.monthlyUsageRepository = monthlyUsageRepository;
		this.rateLimitProxyService = rateLimitProxyService;
		this.timeProvider = timeProvider;
	}

	public QuotaStatusResponse buildStatus(AppUser user) {
		QuotaStatusResponse r = new QuotaStatusResponse();
		r.setPlan(user.getPlan().name());
		int ym = timeProvider.currentYearMonthInt();
		long used = monthlyUsageRepository.findByUserAndYearMonth(user, ym)
				.map(MonthlyUsage::getTokensConsumed)
				.orElse(0L);
		r.setTokensUsedThisMonth(used);
		Long cap = user.getPlan().getMonthlyTokenLimit();
		r.setMonthlyTokenLimit(cap);
		if (cap == null) {
			r.setTokensRemainingThisMonth(null);
		}
		else {
			r.setTokensRemainingThisMonth(Math.max(0L, cap - used));
		}
		r.setMonthlyResetAt(timeProvider.firstInstantOfNextMonth());
		r.setRateLimit(rateLimitProxyService.snapshot(user));
		return r;
	}
}
