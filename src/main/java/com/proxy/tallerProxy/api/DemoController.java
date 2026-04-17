package com.proxy.tallerProxy.api;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proxy.tallerProxy.api.dto.DemoUserResponse;
import com.proxy.tallerProxy.domain.AppUser;
import com.proxy.tallerProxy.domain.Plan;
import com.proxy.tallerProxy.repository.AppUserRepository;
import com.proxy.tallerProxy.service.RateLimitProxyService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/demo")
@Tag(name = "Demo", description = "Local workshop helpers (user listing, rate-limit reset).")
public class DemoController {

	private final AppUserRepository appUserRepository;
	private final RateLimitProxyService rateLimitProxyService;

	public DemoController(AppUserRepository appUserRepository, RateLimitProxyService rateLimitProxyService) {
		this.appUserRepository = appUserRepository;
		this.rateLimitProxyService = rateLimitProxyService;
	}

	@Operation(summary = "List seeded demo users and plan limits")
	@GetMapping("/users")
	public List<DemoUserResponse> listDemoUsers() {
		return appUserRepository.findAll().stream()
				.sorted(Comparator.comparing(AppUser::getExternalId))
				.map(this::toDemoUser)
				.collect(Collectors.toList());
	}

	@Operation(summary = "Clear in-memory per-minute rate limit counters (all users)")
	@PostMapping("/reset-rate-limit")
	public void resetRateLimit() {
		rateLimitProxyService.resetMinuteWindow();
	}

	private DemoUserResponse toDemoUser(AppUser user) {
		Plan p = user.getPlan();
		Integer rpm = p == Plan.ENTERPRISE ? null : p.getRequestsPerMinute();
		String label = user.getExternalId() + " (" + p.name() + ")";
		return new DemoUserResponse(user.getExternalId(), p.name(), rpm, p.getMonthlyTokenLimit(), label);
	}
}
