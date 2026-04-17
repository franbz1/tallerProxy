package com.proxy.tallerProxy.api.dto;

import javax.validation.constraints.NotBlank;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Prompt for mock text generation. Token charge uses ceil(len/4) + fixed output estimate on the server.")
public class GenerationRequest {

	@NotBlank
	@Schema(example = "Explain rate limiting in one sentence.")
	private String prompt;

	public String getPrompt() {
		return prompt;
	}

	public void setPrompt(String prompt) {
		this.prompt = prompt;
	}
}
