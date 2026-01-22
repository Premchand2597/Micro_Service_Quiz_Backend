package com.telusko.apigateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiGatewayConfiguration {
	
	@Bean
	RouteLocator gatewayRouter(RouteLocatorBuilder builder) {
		return builder.routes()
				.route(p -> p.path("/api/auth/**")
						.uri("lb://AUTH-SERVICE"))
				.route(p -> p.path("/quiz/**")
						.uri("lb://QUIZ-SERVICE"))
				.route(p -> p.path("/question/**")
						.uri("lb://QUESTION-SERVICE"))
				.build();
	}
}
