package com.proxy.tallerProxy.domain;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "daily_usage", uniqueConstraints = {
		@UniqueConstraint(name = "uk_daily_user_date", columnNames = { "user_id", "usage_date" })
})
public class DailyUsage {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "user_id", nullable = false)
	private AppUser user;

	@Column(name = "usage_date", nullable = false)
	private LocalDate usageDate;

	@Column(name = "tokens_consumed", nullable = false)
	private long tokensConsumed;

	protected DailyUsage() {
	}

	public DailyUsage(AppUser user, LocalDate usageDate, long tokensConsumed) {
		this.user = user;
		this.usageDate = usageDate;
		this.tokensConsumed = tokensConsumed;
	}

	public Long getId() {
		return id;
	}

	public AppUser getUser() {
		return user;
	}

	public void setUser(AppUser user) {
		this.user = user;
	}

	public LocalDate getUsageDate() {
		return usageDate;
	}

	public void setUsageDate(LocalDate usageDate) {
		this.usageDate = usageDate;
	}

	public long getTokensConsumed() {
		return tokensConsumed;
	}

	public void setTokensConsumed(long tokensConsumed) {
		this.tokensConsumed = tokensConsumed;
	}
}
