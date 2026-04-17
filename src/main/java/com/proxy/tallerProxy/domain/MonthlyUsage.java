package com.proxy.tallerProxy.domain;

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
@Table(name = "monthly_usage", uniqueConstraints = {
		@UniqueConstraint(name = "uk_monthly_user_period", columnNames = { "user_id", "year_month" })
})
public class MonthlyUsage {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "user_id", nullable = false)
	private AppUser user;

	/**
	 * Encoded as yyyyMM (e.g. 202604).
	 */
	@Column(name = "year_month", nullable = false)
	private int yearMonth;

	@Column(name = "tokens_consumed", nullable = false)
	private long tokensConsumed;

	protected MonthlyUsage() {
	}

	public MonthlyUsage(AppUser user, int yearMonth, long tokensConsumed) {
		this.user = user;
		this.yearMonth = yearMonth;
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

	public int getYearMonth() {
		return yearMonth;
	}

	public void setYearMonth(int yearMonth) {
		this.yearMonth = yearMonth;
	}

	public long getTokensConsumed() {
		return tokensConsumed;
	}

	public void setTokensConsumed(long tokensConsumed) {
		this.tokensConsumed = tokensConsumed;
	}
}
