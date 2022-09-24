package com.serwa.boilerplate.palindrome.application

import com.serwa.boilerplate.palindrome.domain.model.Palindrome
import com.serwa.boilerplate.palindrome.domain.model.Word
import com.serwa.boilerplate.palindrome.domain.ports.PalindromePublisher
import com.serwa.boilerplate.palindrome.domain.ports.PalindromeRepository
import com.serwa.boilerplate.palindrome.domain.service.PalindromeService
import com.serwa.boilerplate.palindrome.infrastructure.adapters.restapi.CreatePalindromeRequest
import java.time.Clock

class PalindromeFacade(
	palindromeRepository: PalindromeRepository,
	palindromePublisher: PalindromePublisher,
	clock: Clock
) {

	private val palindromeService = PalindromeService(palindromeRepository, palindromePublisher, clock)

	fun handle(createPalindromeRequest: CreatePalindromeRequest): PalindromeResult {
		val word = Word(createPalindromeRequest.word)

		return palindromeService.palindromeFrom(word).toResult()
	}
}

private fun Palindrome.toResult(): PalindromeResult = PalindromeResult(
	source = source.raw,
	result = result.raw
)

data class PalindromeResult(val source: String, val result: String)
