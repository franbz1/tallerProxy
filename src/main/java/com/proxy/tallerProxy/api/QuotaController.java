package com.proxy.tallerProxy.api;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proxy.tallerProxy.api.dto.DailyUsageHistoryResponse;
import com.proxy.tallerProxy.api.dto.QuotaStatusResponse;
import com.proxy.tallerProxy.api.dto.UpgradeRequest;
import com.proxy.tallerProxy.api.dto.UpgradeResponse;
import com.proxy.tallerProxy.service.PlanUpgradeService;
import com.proxy.tallerProxy.service.QuotaHistoryService;
import com.proxy.tallerProxy.service.QuotaStatusService;
import com.proxy.tallerProxy.service.UserLookupService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/quota")
@Tag(name = "Quota", description = "Quota status, usage history, and plan upgrade (FREE → PRO).")
public class QuotaController {

	private final UserLookupService userLookupService;
	private final QuotaStatusService quotaStatusService;
	private final QuotaHistoryService quotaHistoryService;
	private final PlanUpgradeService planUpgradeService;

	public QuotaController(UserLookupService userLookupService,
			QuotaStatusService quotaStatusService,
			QuotaHistoryService quotaHistoryService,
			PlanUpgradeService planUpgradeService) {
		this.userLookupService = userLookupService;
		this.quotaStatusService = quotaStatusService;
		this.quotaHistoryService = quotaHistoryService;
		this.planUpgradeService = planUpgradeService;
	}

	@Operation(summary = "Current quota and rate limit snapshot")
	@GetMapping("/status")
	public QuotaStatusResponse status(
			@Parameter(required = true, example = "demo-free") @RequestHeader(AppHeaders.X_USER_ID) String userId) {
		return quotaStatusService.buildStatus(userLookupService.requireByExternalId(userId));
	}

	@Operation(summary = "Daily token usage for the last 7 days (including zeros)")
	@GetMapping("/history")
	public DailyUsageHistoryResponse history(
			@Parameter(required = true, example = "demo-free") @RequestHeader(AppHeaders.X_USER_ID) String userId) {
		return quotaHistoryService.lastSevenDays(userLookupService.requireByExternalId(userId));
	}

	@Operation(summary = "Upgrade plan from FREE to PRO (mock payment)")
	@ApiResponse(responseCode = "200", description = "New plan",
			content = @Content(schema = @Schema(implementation = UpgradeResponse.class)))
	@PostMapping("/upgrade")
	public UpgradeResponse upgrade(
			@Parameter(required = true, example = "demo-free") @RequestHeader(AppHeaders.X_USER_ID) String userId,
			@Valid @RequestBody UpgradeRequest request) {
		return planUpgradeService.upgrade(userId, request);
	}
}
