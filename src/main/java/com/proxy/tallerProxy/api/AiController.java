package com.proxy.tallerProxy.api;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proxy.tallerProxy.api.dto.GenerationRequest;
import com.proxy.tallerProxy.api.dto.GenerationResponse;
import com.proxy.tallerProxy.service.AIGenerationOrchestratorService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/ai")
@Tag(name = "AI Generation", description = "Mock AI text generation behind rate limit and quota proxies.")
public class AiController {

	private final AIGenerationOrchestratorService orchestratorService;

	public AiController(AIGenerationOrchestratorService orchestratorService) {
		this.orchestratorService = orchestratorService;
	}

	@Operation(summary = "Generate mock AI text",
			description = "Runs RateLimit → Quota → Mock AI. Charges ceil(len(prompt)/4) + fixed output estimate tokens. "
					+ "Returns refreshed quota status for UI.")
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "Generated text and quota snapshot",
					content = @Content(schema = @Schema(implementation = GenerationResponse.class))),
			@ApiResponse(responseCode = "402", description = "Monthly token quota exhausted",
					content = @Content(schema = @Schema(implementation = com.proxy.tallerProxy.api.dto.ApiErrorResponse.class))),
			@ApiResponse(responseCode = "429", description = "Per-minute request limit exceeded; Retry-After header set",
					content = @Content(schema = @Schema(implementation = com.proxy.tallerProxy.api.dto.ApiErrorResponse.class)))
	})
	@PostMapping("/generate")
	public GenerationResponse generate(
			@Parameter(description = "Logical end-user id (demo: demo-free, demo-pro, demo-enterprise)", required = true,
					example = "demo-free")
			@RequestHeader(AppHeaders.X_USER_ID) String userId,
			@Valid @RequestBody GenerationRequest request) {
		return orchestratorService.generate(userId, request);
	}
}
