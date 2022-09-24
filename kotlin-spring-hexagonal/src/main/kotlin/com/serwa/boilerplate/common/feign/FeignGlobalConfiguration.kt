package com.serwa.boilerplate.common.feign

import feign.Logger
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@EnableFeignClients(basePackages = ["com.serwa.*"])
class FeignGlobalConfiguration {

	@Bean
	fun feignLoggerLevel(): Logger.Level = Logger.Level.FULL
}

class FeignProperties(
	var connectionTimeoutMillis: Long = 200,
	var readTimeoutMillis: Long = 500,
	var maxIdleConnections: Int = 10,
	var keepAliveDurationMinutes: Long = 5
)
