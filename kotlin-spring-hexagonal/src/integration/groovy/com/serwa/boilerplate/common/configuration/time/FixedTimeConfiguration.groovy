package com.serwa.boilerplate.common.configuration.time

import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.core.env.Environment

import java.time.Clock
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@TestConfiguration
class FixedTimeConfiguration {

	public static final String FIXED_DAY = "1994-12-05"
	public static final String FIXED_TIME = "$FIXED_DAY 00:00:00"
	private static final UTC_ZONE = ZoneId.of("UTC")

	@Bean
	Clock clock(Environment environment) {
		DateTimeFormatter formatter = DateTimeFormatter
			.ofPattern("yyyy-MM-dd HH:mm:ss")
			.withZone(UTC_ZONE)

		final Instant from

		if (environment.activeProfiles.contains("forecastSpecsClock")) {
			from = Instant.from(formatter.parse("1994-01-01 00:00:00"))
		} else {
			from = Instant.from(formatter.parse(FIXED_TIME))
		}

		return Clock.fixed(from, UTC_ZONE)
	}
}
