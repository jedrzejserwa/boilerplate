package com.serwa.boilerplate.palindrome.infrastructure.configuration.feign

import com.serwa.boilerplate.common.feign.FeignProperties
import okhttp3.ConnectionPool
import okhttp3.OkHttpClient
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.concurrent.TimeUnit

@Configuration
class NbpFeignClientConfiguration {

	@Bean
	@ConfigurationProperties("feign.nbp-api")
	fun feignNbpApiProperties() = FeignProperties()

	@Bean
	fun nbpClient(feignNbpApiProperties: FeignProperties): OkHttpClient {
		return OkHttpClient.Builder()
			.connectTimeout(feignNbpApiProperties.connectionTimeoutMillis, TimeUnit.MILLISECONDS)
			.readTimeout(feignNbpApiProperties.readTimeoutMillis, TimeUnit.MILLISECONDS)
			.connectionPool(
				ConnectionPool(
					feignNbpApiProperties.maxIdleConnections, feignNbpApiProperties.keepAliveDurationMinutes,
					TimeUnit.MINUTES
				)
			)
			.build()
	}
}
