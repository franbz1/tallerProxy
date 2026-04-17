package com.proxy.tallerProxy.service;

import java.time.LocalDate;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.proxy.tallerProxy.api.exception.QuotaExceededException;
import com.proxy.tallerProxy.domain.AppUser;
import com.proxy.tallerProxy.domain.DailyUsage;
import com.proxy.tallerProxy.domain.MonthlyUsage;
import com.proxy.tallerProxy.repository.DailyUsageRepository;
import com.proxy.tallerProxy.repository.MonthlyUsageRepository;

@Service
public class QuotaProxyService {

	private final MonthlyUsageRepository monthlyUsageRepository;
	private final DailyUsageRepository dailyUsageRepository;
	private final TimeProvider timeProvider;

	public QuotaProxyService(MonthlyUsageRepository monthlyUsageRepository,
			DailyUsageRepository dailyUsageRepository,
			TimeProvider timeProvider) {
		this.monthlyUsageRepository = monthlyUsageRepository;
		this.dailyUsageRepository = dailyUsageRepository;
		this.timeProvider = timeProvider;
	}

	@Transactional
	public void consumeTokens(AppUser user, long tokens) {
		if (tokens <= 0) {
			throw new IllegalArgumentException("tokens must be positive");
		}
		int ym = timeProvider.currentYearMonthInt();
		LocalDate today = timeProvider.today();

		MonthlyUsage monthly = monthlyUsageRepository.findByUserAndYearMonth(user, ym)
				.orElseGet(() -> monthlyUsageRepository.save(new MonthlyUsage(user, ym, 0L)));

		long projected = monthly.getTokensConsumed() + tokens;
		Long cap = user.getPlan().getMonthlyTokenLimit();
		if (cap != null && projected > cap) {
			throw new QuotaExceededException("Monthly token quota exceeded for plan " + user.getPlan() + ".");
		}
		monthly.setTokensConsumed(projected);
		monthlyUsageRepository.save(monthly);

		DailyUsage daily = dailyUsageRepository.findByUserAndUsageDate(user, today)
				.orElseGet(() -> new DailyUsage(user, today, 0L));
		daily.setTokensConsumed(daily.getTokensConsumed() + tokens);
		dailyUsageRepository.save(daily);
	}
}
