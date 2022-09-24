package com.serwa.boilerplate.palindrome.infrastructure.configuration

import com.serwa.boilerplate.palindrome.application.PalindromeFacade
import com.serwa.boilerplate.palindrome.domain.model.Palindrome
import com.serwa.boilerplate.palindrome.domain.model.PalindromeCreatedEvent
import com.serwa.boilerplate.palindrome.domain.model.Word
import com.serwa.boilerplate.palindrome.domain.ports.PalindromePublisher
import com.serwa.boilerplate.palindrome.domain.ports.PalindromeRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Component
import java.time.Clock

@Configuration
class ModuleConfiguration {

	@Bean
	fun moduleFacade(
		palindromeRepository: PalindromeRepository,
		palindromePublisher: PalindromePublisher,
		clock: Clock
	): PalindromeFacade {
		return PalindromeFacade(palindromeRepository, palindromePublisher, clock)
	}
}

@Component
class NoOpPalindromeRepository : PalindromeRepository {

	override fun palindrome(word: Word): Palindrome? {
		return null
	}

	override fun store(palindrome: Palindrome) {
		// noop
	}
}

@Component
class NoOpPalindromePublisher : PalindromePublisher {

	override fun publish(event: PalindromeCreatedEvent) {
		// noop
	}
}
