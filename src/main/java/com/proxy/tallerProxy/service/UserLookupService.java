package com.proxy.tallerProxy.service;

import org.springframework.stereotype.Service;

import com.proxy.tallerProxy.api.exception.UserNotFoundException;
import com.proxy.tallerProxy.domain.AppUser;
import com.proxy.tallerProxy.repository.AppUserRepository;

@Service
public class UserLookupService {

	private final AppUserRepository appUserRepository;

	public UserLookupService(AppUserRepository appUserRepository) {
		this.appUserRepository = appUserRepository;
	}

	public AppUser requireByExternalId(String externalId) {
		if (externalId == null || externalId.isBlank()) {
			throw new IllegalArgumentException("X-User-Id header is required.");
		}
		String trimmed = externalId.trim();
		return appUserRepository.findByExternalId(trimmed)
				.orElseThrow(() -> new UserNotFoundException("User not found for external id: " + trimmed));
	}
}
