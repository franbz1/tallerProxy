package com.proxy.tallerProxy.service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

import org.springframework.stereotype.Component;

import com.proxy.tallerProxy.config.AppProperties;

@Component
public class TimeProvider {

	private final ZoneId zoneId;

	public TimeProvider(AppProperties appProperties) {
		this.zoneId = ZoneId.of(appProperties.getTimezone());
	}

	public ZoneId getZoneId() {
		return zoneId;
	}

	public ZonedDateTime nowZoned() {
		return ZonedDateTime.now(zoneId);
	}

	public LocalDate today() {
		return LocalDate.now(zoneId);
	}

	public int currentYearMonthInt() {
		ZonedDateTime z = nowZoned();
		return z.getYear() * 100 + z.getMonthValue();
	}

	public YearMonth currentYearMonth() {
		return YearMonth.from(nowZoned());
	}

	/**
	 * First instant of the next calendar month in the app zone, as UTC {@link Instant}.
	 */
	public Instant firstInstantOfNextMonth() {
		YearMonth next = YearMonth.from(nowZoned()).plusMonths(1);
		return next.atDay(1).atStartOfDay(zoneId).toInstant();
	}

	public Instant endOfCurrentUtcMinute() {
		return Instant.now().truncatedTo(ChronoUnit.MINUTES).plus(1, ChronoUnit.MINUTES);
	}

	public long secondsToEndOfCurrentUtcMinute() {
		long epochSec = Instant.now().getEpochSecond();
		long into = epochSec % 60;
		return into == 0 ? 60L : 60L - into;
	}
}
