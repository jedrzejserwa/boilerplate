package com.serwa.boilerplate.palindrome.domain.model

import java.time.Instant

data class Word(val raw: String) {

	init {
		require(raw.isNotBlank()) {
			"Text must be not blank"
		}
	}

	fun palindrome(): Word = Word(raw.reversed())
}

data class Palindrome(val source: Word, val result: Word, val createdAt: Timestamp)
data class Timestamp(val raw: Instant)
data class Owner(val raw: String)
