package com.proxy.tallerProxy.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.proxy.tallerProxy.api.dto.UpgradeRequest;
import com.proxy.tallerProxy.api.dto.UpgradeResponse;
import com.proxy.tallerProxy.api.exception.InvalidUpgradeException;
import com.proxy.tallerProxy.domain.AppUser;
import com.proxy.tallerProxy.domain.Plan;
import com.proxy.tallerProxy.repository.AppUserRepository;

@Service
public class PlanUpgradeService {

	private final UserLookupService userLookupService;
	private final AppUserRepository appUserRepository;

	public PlanUpgradeService(UserLookupService userLookupService, AppUserRepository appUserRepository) {
		this.userLookupService = userLookupService;
		this.appUserRepository = appUserRepository;
	}

	@Transactional
	public UpgradeResponse upgrade(String externalUserId, UpgradeRequest request) {
		AppUser user = userLookupService.requireByExternalId(externalUserId);
		if (user.getPlan() != Plan.FREE) {
			throw new InvalidUpgradeException("Upgrade is only supported from FREE to PRO.");
		}
		if (!request.isSimulateSuccess()) {
			throw new InvalidUpgradeException("Mock payment was declined.");
		}
		user.setPlan(Plan.PRO);
		appUserRepository.save(user);
		return new UpgradeResponse(Plan.PRO.name(), true);
	}
}
