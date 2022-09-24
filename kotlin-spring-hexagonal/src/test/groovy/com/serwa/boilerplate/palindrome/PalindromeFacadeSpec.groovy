package com.serwa.boilerplate.palindrome

import com.serwa.boilerplate.common.FakeClock
import com.serwa.boilerplate.palindrome.application.PalindromeFacade
import com.serwa.boilerplate.palindrome.domain.model.PalindromeCreatedEvent
import com.serwa.boilerplate.palindrome.domain.model.Word
import com.serwa.boilerplate.palindrome.domain.ports.PalindromePublisher
import com.serwa.boilerplate.palindrome.domain.ports.PalindromeRepository
import com.serwa.boilerplate.palindrome.infrastructure.adapters.restapi.CreatePalindromeRequest
import spock.lang.Specification
import spock.lang.Unroll

import java.time.Clock

class PalindromeFacadeSpec extends Specification {

	private PalindromeFacade palindromeFacade
	private PalindromeRepository palindromeRepository
	private PalindromePublisher palindromePublisher
	private Clock clock

	def setup() {
		palindromeRepository = Mock(PalindromeRepository)
		palindromePublisher = Mock(PalindromePublisher)
		clock = FakeClock.INSTANCE

		palindromeFacade = new PalindromeFacade(palindromeRepository, palindromePublisher, clock)
	}

	@Unroll("#expected should be palindrome of #from")
	def "should produce palindrome"() {
		given:
			def request = new CreatePalindromeRequest(from)

		when:
			def result = palindromeFacade.handle(request)

		then:
			result.result == expected

		and:
			1 * palindromeRepository.palindrome(_ as Word) >> null
			1 * palindromePublisher.publish(_ as PalindromeCreatedEvent)

		where:
			from    | expected
			"123"   | "321"
			"kajak" | "kajak"
			"value" | "eulav"
			"null"  | "llun"
	}
}
