package com.proxy.tallerProxy.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "app_users")
public class AppUser {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "external_id", nullable = false, unique = true, length = 128)
	private String externalId;

	@Enumerated(EnumType.STRING)
	@Column(name = "subscription_plan", nullable = false, length = 32)
	private Plan plan;

	protected AppUser() {
	}

	public AppUser(String externalId, Plan plan) {
		this.externalId = externalId;
		this.plan = plan;
	}

	public Long getId() {
		return id;
	}

	public String getExternalId() {
		return externalId;
	}

	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}

	public Plan getPlan() {
		return plan;
	}

	public void setPlan(Plan plan) {
		this.plan = plan;
	}
}
