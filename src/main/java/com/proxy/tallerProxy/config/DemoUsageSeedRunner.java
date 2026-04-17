package com.proxy.tallerProxy.config;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.proxy.tallerProxy.domain.AppUser;
import com.proxy.tallerProxy.domain.DailyUsage;
import com.proxy.tallerProxy.domain.MonthlyUsage;
import com.proxy.tallerProxy.repository.AppUserRepository;
import com.proxy.tallerProxy.repository.DailyUsageRepository;
import com.proxy.tallerProxy.repository.MonthlyUsageRepository;
import com.proxy.tallerProxy.service.TimeProvider;

/**
 * Seeds synthetic daily/monthly usage for demo users so the 7-day chart and quota bars show data on first load.
 */
@Component
@Order(100)
public class DemoUsageSeedRunner implements CommandLineRunner {

	private final AppUserRepository appUserRepository;
	private final DailyUsageRepository dailyUsageRepository;
	private final MonthlyUsageRepository monthlyUsageRepository;
	private final TimeProvider timeProvider;

	public DemoUsageSeedRunner(AppUserRepository appUserRepository,
			DailyUsageRepository dailyUsageRepository,
			MonthlyUsageRepository monthlyUsageRepository,
			TimeProvider timeProvider) {
		this.appUserRepository = appUserRepository;
		this.dailyUsageRepository = dailyUsageRepository;
		this.monthlyUsageRepository = monthlyUsageRepository;
		this.timeProvider = timeProvider;
	}

	@Override
	@Transactional
	public void run(String... args) {
		Map<String, long[]> tokensByExternalId = new LinkedHashMap<>();
		tokensByExternalId.put("demo-free", new long[] { 400, 600, 500, 1200, 800, 1500, 2000 });
		tokensByExternalId.put("demo-pro", new long[] { 5000, 8000, 3500, 12000, 9000, 6000, 6500 });
		tokensByExternalId.put("demo-enterprise", new long[] { 10000, 15000, 8000, 20000, 12000, 25000, 22000 });
		tokensByExternalId.put("demo-rate-limit", new long[] { 100, 200, 150, 300, 250, 400, 600 });
		// Sum below 50k so at least one typical generation (~101+ tokens) still fits; second hits 402.
		tokensByExternalId.put("demo-near-quota", new long[] { 6800, 6300, 7800, 7300, 6900, 6700, 6700 });

		int ym = timeProvider.currentYearMonthInt();
		LocalDate end = timeProvider.today();
		LocalDate start = end.minusDays(6);

		for (Map.Entry<String, long[]> e : tokensByExternalId.entrySet()) {
			Optional<AppUser> userOpt = appUserRepository.findByExternalId(e.getKey());
			if (userOpt.isEmpty()) {
				continue;
			}
			AppUser user = userOpt.get();
			dailyUsageRepository.deleteByUser(user);
			monthlyUsageRepository.deleteByUser(user);

			long[] daily = e.getValue();
			long monthSum = 0L;
			for (int i = 0; i < daily.length; i++) {
				LocalDate d = start.plusDays(i);
				long t = daily[i];
				monthSum += t;
				dailyUsageRepository.save(new DailyUsage(user, d, t));
			}
			monthlyUsageRepository.save(new MonthlyUsage(user, ym, monthSum));
		}
	}
}
