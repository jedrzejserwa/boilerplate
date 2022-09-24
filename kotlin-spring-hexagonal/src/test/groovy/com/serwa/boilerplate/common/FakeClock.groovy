package com.serwa.boilerplate.common

import java.time.Clock
import java.time.Duration
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class FakeClock extends Clock {

    static final INSTANCE = new FakeClock()

    private static final String FIXED_TIME = "1994-12-05 00:00:00" // Monday

    private ZoneId zoneId

    private Instant instant

    FakeClock(String raw) {
        this.zoneId = ZoneId.of("UTC")

        DateTimeFormatter formatter = DateTimeFormatter
                .ofPattern("yyyy-MM-dd HH:mm:ss")
                .withZone(zoneId)

        this.instant = Instant.from(formatter.parse(raw))
    }

    FakeClock(ZoneId zoneId, Instant instant) {
        this.zoneId = zoneId

        if (instant == null) {
            DateTimeFormatter formatter = DateTimeFormatter
                    .ofPattern("yyyy-MM-dd HH:mm:ss")
                    .withZone(zoneId)

            this.instant = Instant.from(formatter.parse(FIXED_TIME))
        } else {
            this.instant = instant
        }
    }

    FakeClock() {
        this(ZoneId.of("UTC"), null)
    }

    @Override
    ZoneId getZone() {
        return zoneId
    }

    @Override
    Clock withZone(ZoneId zone) {
        return new FakeClock(zoneId, instant)
    }

    @Override
    Instant instant() {
        return instant
    }

    Instant someSaturday() {
        return this.instant() - Duration.ofDays(2)
    }

    Instant someSunday() {
        return this.instant() - Duration.ofDays(1)
    }

    Clock minus(Duration duration) {
        return new FakeClock(zoneId, instant - duration)
    }

    Clock plus(Duration duration) {
        return new FakeClock(zoneId, instant + duration)
    }

    void timePasses(Duration duration) {
        this.instant + duration
    }
}
