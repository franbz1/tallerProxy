package com.proxy.tallerProxy.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.proxy.tallerProxy.api.dto.GenerationRequest;
import com.proxy.tallerProxy.api.dto.GenerationResponse;
import com.proxy.tallerProxy.domain.AppUser;

@Service
public class AIGenerationOrchestratorService {

	private final UserLookupService userLookupService;
	private final RateLimitProxyService rateLimitProxyService;
	private final QuotaProxyService quotaProxyService;
	private final TokenEstimator tokenEstimator;
	private final AIGenerationService aiGenerationService;
	private final QuotaStatusService quotaStatusService;

	public AIGenerationOrchestratorService(UserLookupService userLookupService,
			RateLimitProxyService rateLimitProxyService,
			QuotaProxyService quotaProxyService,
			TokenEstimator tokenEstimator,
			AIGenerationService aiGenerationService,
			QuotaStatusService quotaStatusService) {
		this.userLookupService = userLookupService;
		this.rateLimitProxyService = rateLimitProxyService;
		this.quotaProxyService = quotaProxyService;
		this.tokenEstimator = tokenEstimator;
		this.aiGenerationService = aiGenerationService;
		this.quotaStatusService = quotaStatusService;
	}

	@Transactional(rollbackFor = Exception.class)
	public GenerationResponse generate(String externalUserId, GenerationRequest request) {
		AppUser user = userLookupService.requireByExternalId(externalUserId);

		long inputEstimate = tokenEstimator.estimatePromptTokens(request.getPrompt());
		long outputEstimate = tokenEstimator.fixedOutputEstimate();
		long totalCharge = inputEstimate + outputEstimate;

		rateLimitProxyService.validateAndIncrement(user);
		quotaProxyService.consumeTokens(user, totalCharge);

		GenerationResponse generated = aiGenerationService.generate(request);

		GenerationResponse response = new GenerationResponse();
		response.setText(generated.getText());
		response.setTokensCharged(totalCharge);
		response.setEstimatedInputTokens(inputEstimate);
		response.setEstimatedOutputTokens(outputEstimate);
		response.setQuotaStatus(quotaStatusService.buildStatus(user));
		return response;
	}
}
