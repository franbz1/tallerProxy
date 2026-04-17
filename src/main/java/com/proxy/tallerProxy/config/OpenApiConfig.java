package com.proxy.tallerProxy.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApiConfig {

	@Bean
	public OpenAPI tallerProxyOpenApi() {
		return new OpenAPI()
				.info(new Info()
						.title("AI consumption platform (taller)")
						.version("1.0.0")
						.description("Users are identified with the `X-User-Id` header (see demo users in data.sql). "
								+ "Token charge per generate call: **ceil(length(prompt)/4) + fixed output estimate** "
								+ "(see `app.token-estimation.fixed-output-tokens`, default 100). "
								+ "Rate limit returns **429** with **Retry-After** (seconds). "
								+ "Quota exhaustion returns **402 Payment Required**."));
	}
}
