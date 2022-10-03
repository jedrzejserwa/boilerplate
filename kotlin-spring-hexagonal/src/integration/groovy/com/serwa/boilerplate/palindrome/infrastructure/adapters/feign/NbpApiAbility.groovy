package com.serwa.boilerplate.palindrome.infrastructure.adapters.feign

import com.serwa.boilerplate.common.adapters.rest.RestitoAware

import java.util.regex.Pattern

import static com.xebialabs.restito.builder.stub.StubHttp.whenHttp
import static com.xebialabs.restito.builder.verify.VerifyHttp.verifyHttp
import static com.xebialabs.restito.semantics.Action.header
import static com.xebialabs.restito.semantics.Action.ok
import static com.xebialabs.restito.semantics.Condition.get
import static com.xebialabs.restito.semantics.Condition.matchesUri
import static java.util.regex.Pattern.compile

trait NbpApiAbility implements RestitoAware {

	void nbpApiReturnsExchangeRateForUSD(BigDecimal exchangeRate) {
		nbpApiReturnsExchangeRateFor("USD", exchangeRate, null)
	}

	void nbpApiReturnsExchangeRateForEUR(BigDecimal exchangeRate) {
		nbpApiReturnsExchangeRateFor("EUR", exchangeRate, null)
	}

	void nbpApiReturnsExchangeRateForEUR(BigDecimal exchangeRate, String date) {
		nbpApiReturnsExchangeRateFor("EUR", exchangeRate, date)
	}

	void nbpApiReturnsExchangeRateForCHF(BigDecimal exchangeRate, String date) {
		nbpApiReturnsExchangeRateFor("CHF", exchangeRate, null)
	}

	private void nbpApiReturnsExchangeRateFor(String currency, BigDecimal exchangeRate, String date) {
		whenHttp(stubServer()).match(
			matchesUri(compile("/api/exchangerates/rates/A/$currency/.*", Pattern.DOTALL)))
			.then(
				ok(),
				header("Content-Type", "application/json"),
				jsonResponse([
					table   : "A",
					currency: "dolar amerykański",
					code    : currency,
					rates   : [
						[
							no           : "069/A/NBP/2022",
							effectiveDate: date,
							mid          : exchangeRate
						]
					]
				])
			)
	}

	void verifyNbpApiNotCalledForUSDExchangeRate() {
		verifyHttp(stubServer()).never(get("/api/exchangerates/rates/A/USD"))
	}

	void verifyNbpApiCalledOnceForUSDExchangeRate() {
		verifyNbpApiCalledOnceForExchangeRateFor("USD")
	}

	void verifyNbpApiCalledOnceForEURExchangeRate() {
		verifyNbpApiCalledOnceForExchangeRateFor("EUR")
	}

	void verifyNbpApiCalledOnceForCHFExchangeRate() {
		verifyNbpApiCalledOnceForExchangeRateFor("CHF")
	}

	private void verifyNbpApiCalledOnceForExchangeRateFor(String currency) {
		verifyHttp(stubServer()).once(
			matchesUri(compile("/api/exchangerates/rates/A/$currency/.*", Pattern.DOTALL))
		)
	}
}
