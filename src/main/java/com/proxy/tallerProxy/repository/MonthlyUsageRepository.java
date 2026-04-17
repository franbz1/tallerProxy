package com.proxy.tallerProxy.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.proxy.tallerProxy.domain.AppUser;
import com.proxy.tallerProxy.domain.MonthlyUsage;

public interface MonthlyUsageRepository extends JpaRepository<MonthlyUsage, Long> {

	Optional<MonthlyUsage> findByUserAndYearMonth(AppUser user, int yearMonth);

	@Modifying
	@Query("DELETE FROM MonthlyUsage m WHERE m.yearMonth < :beforeYm")
	int deleteByYearMonthBefore(@Param("beforeYm") int beforeYm);
}
