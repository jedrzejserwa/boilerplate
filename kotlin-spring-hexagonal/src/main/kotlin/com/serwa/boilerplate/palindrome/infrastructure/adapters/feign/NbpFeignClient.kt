package com.serwa.boilerplate.palindrome.infrastructure.adapters.feign

import com.serwa.boilerplate.palindrome.infrastructure.configuration.feign.NbpFeignClientConfiguration
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import java.math.BigDecimal

/**
 * http://api.nbp.pl
 */
@FeignClient(
	name = "nbpApi",
	url = "\${feign.nbpApi.url}",
	configuration = [NbpFeignClientConfiguration::class]
)
interface NbpFeignClient {

	@RequestMapping(
		method = [RequestMethod.GET],
		value = ["/api/exchangerates/rates/{tableName}/{currencyCode}/{from}/{to}"]
	)
	fun getExchangeRates(
		@PathVariable tableName: String,
		@PathVariable currencyCode: String,
		@PathVariable from: String,
		@PathVariable to: String
	): NbpExchangeRateResponse
}

data class NbpExchangeRateResponse(val rates: List<NbpExchangeRate>)

data class NbpExchangeRate(val effectiveDate: String, val mid: BigDecimal)
