package com.proxy.tallerProxy.service;

import com.proxy.tallerProxy.api.dto.GenerationRequest;
import com.proxy.tallerProxy.api.dto.GenerationResponse;

public interface AIGenerationService {

	GenerationResponse generate(GenerationRequest request);
}
