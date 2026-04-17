package com.proxy.tallerProxy.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

	private final AppProperties appProperties;

	public CorsConfig(AppProperties appProperties) {
		this.appProperties = appProperties;
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				List<String> origins = appProperties.getCorsAllowedOriginsList();
				if (origins.isEmpty()) {
					return;
				}
				registry.addMapping("/api/**")
						.allowedOrigins(origins.toArray(new String[0]))
						.allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
						.allowedHeaders("*")
						.exposedHeaders("Retry-After")
						.allowCredentials(false);
			}
		};
	}
}
