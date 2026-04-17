package com.proxy.tallerProxy.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proxy.tallerProxy.domain.AppUser;
import com.proxy.tallerProxy.domain.DailyUsage;

public interface DailyUsageRepository extends JpaRepository<DailyUsage, Long> {

	Optional<DailyUsage> findByUserAndUsageDate(AppUser user, LocalDate usageDate);

	List<DailyUsage> findByUserAndUsageDateBetweenOrderByUsageDateAsc(AppUser user, LocalDate from, LocalDate to);

	void deleteByUsageDateBefore(LocalDate cutoff);
}
