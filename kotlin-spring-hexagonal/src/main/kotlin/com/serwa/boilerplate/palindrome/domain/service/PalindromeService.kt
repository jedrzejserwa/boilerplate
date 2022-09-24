package com.serwa.boilerplate.palindrome.domain.service

import com.serwa.boilerplate.palindrome.domain.model.Palindrome
import com.serwa.boilerplate.palindrome.domain.model.PalindromeCreatedEvent
import com.serwa.boilerplate.palindrome.domain.model.Timestamp
import com.serwa.boilerplate.palindrome.domain.model.Word
import com.serwa.boilerplate.palindrome.domain.ports.PalindromePublisher
import com.serwa.boilerplate.palindrome.domain.ports.PalindromeRepository
import java.time.Clock

class PalindromeService(
	private val palindromeRepository: PalindromeRepository,
	private val palindromePublisher: PalindromePublisher,
	private val clock: Clock
) {

	fun palindromeFrom(source: Word): Palindrome {
		val result = palindromeRepository.palindrome(source)

		if (result != null) {
			return result
		}

		val palindromeResult = source.palindrome()

		val palindrome = Palindrome(
			source = source,
			result = palindromeResult,
			createdAt = Timestamp(clock.instant())
		)

		palindromeRepository.store(palindrome)
		palindromePublisher.publish(
			PalindromeCreatedEvent(
				source = palindrome.source,
				result = palindrome.result,
				createdAt = palindrome.createdAt
			)
		)

		return palindrome
	}
}
