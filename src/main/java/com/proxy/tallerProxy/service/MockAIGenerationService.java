package com.proxy.tallerProxy.service;

import java.util.concurrent.ThreadLocalRandom;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.proxy.tallerProxy.api.dto.GenerationRequest;
import com.proxy.tallerProxy.api.dto.GenerationResponse;

@Service
@Primary
public class MockAIGenerationService implements AIGenerationService {

	private static final String[] RESPONSES = {
			"The quick brown fox adapts to rate limits and quotas.",
			"In a simulated world, every token counts toward the monthly budget.",
			"Proxies guard the model: first throttle, then bill, then generate.",
			"Your prompt inspired a deterministic yet random paragraph.",
			"Latency is part of the story: patience yields synthetic wisdom."
	};

	@Override
	public GenerationResponse generate(GenerationRequest request) {
		try {
			Thread.sleep(1200L);
		}
		catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			throw new IllegalStateException("Interrupted during mock AI generation", e);
		}
		String text = RESPONSES[ThreadLocalRandom.current().nextInt(RESPONSES.length)];
		GenerationResponse response = new GenerationResponse();
		response.setText(text);
		return response;
	}
}
