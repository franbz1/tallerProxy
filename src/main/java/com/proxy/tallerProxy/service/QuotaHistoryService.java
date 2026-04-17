package com.proxy.tallerProxy.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.proxy.tallerProxy.api.dto.DailyUsageHistoryResponse;
import com.proxy.tallerProxy.api.dto.DailyUsagePoint;
import com.proxy.tallerProxy.domain.AppUser;
import com.proxy.tallerProxy.domain.DailyUsage;
import com.proxy.tallerProxy.repository.DailyUsageRepository;

@Service
public class QuotaHistoryService {

	private final DailyUsageRepository dailyUsageRepository;
	private final TimeProvider timeProvider;

	public QuotaHistoryService(DailyUsageRepository dailyUsageRepository, TimeProvider timeProvider) {
		this.dailyUsageRepository = dailyUsageRepository;
		this.timeProvider = timeProvider;
	}

	public DailyUsageHistoryResponse lastSevenDays(AppUser user) {
		LocalDate end = timeProvider.today();
		LocalDate start = end.minusDays(6);
		List<DailyUsage> rows = dailyUsageRepository
				.findByUserAndUsageDateBetweenOrderByUsageDateAsc(user, start, end);
		Map<LocalDate, Long> map = new HashMap<>();
		for (DailyUsage d : rows) {
			map.put(d.getUsageDate(), d.getTokensConsumed());
		}
		List<DailyUsagePoint> points = new ArrayList<>();
		for (LocalDate d = start; !d.isAfter(end); d = d.plusDays(1)) {
			points.add(new DailyUsagePoint(d, map.getOrDefault(d, 0L)));
		}
		DailyUsageHistoryResponse response = new DailyUsageHistoryResponse();
		response.setDays(points);
		return response;
	}
}
