package com.proxy.tallerProxy.service;

import java.time.YearMonth;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.proxy.tallerProxy.config.AppProperties;
import com.proxy.tallerProxy.repository.DailyUsageRepository;
import com.proxy.tallerProxy.repository.MonthlyUsageRepository;

@Component
public class ScheduledQuotaJobs {

	private final RateLimitProxyService rateLimitProxyService;
	private final MonthlyUsageRepository monthlyUsageRepository;
	private final DailyUsageRepository dailyUsageRepository;
	private final TimeProvider timeProvider;

	public ScheduledQuotaJobs(RateLimitProxyService rateLimitProxyService,
			MonthlyUsageRepository monthlyUsageRepository,
			DailyUsageRepository dailyUsageRepository,
			TimeProvider timeProvider) {
		this.rateLimitProxyService = rateLimitProxyService;
		this.monthlyUsageRepository = monthlyUsageRepository;
		this.dailyUsageRepository = dailyUsageRepository;
		this.timeProvider = timeProvider;
	}

	@Scheduled(cron = "0 * * * * *")
	public void resetRateLimitWindowEveryMinute() {
		rateLimitProxyService.resetMinuteWindow();
	}

	@Scheduled(cron = "0 0 0 1 * *", zone = "${app.timezone}")
	@Transactional
	public void monthlyQuotaMaintenance() {
		YearMonth cutoffYm = YearMonth.from(timeProvider.nowZoned()).minusMonths(12);
		int beforeYm = cutoffYm.getYear() * 100 + cutoffYm.getMonthValue();
		monthlyUsageRepository.deleteByYearMonthBefore(beforeYm);
		dailyUsageRepository.deleteByUsageDateBefore(timeProvider.today().minusDays(400));
	}
}
