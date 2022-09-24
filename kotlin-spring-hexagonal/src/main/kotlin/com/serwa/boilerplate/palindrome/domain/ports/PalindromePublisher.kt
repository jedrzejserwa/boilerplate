package com.serwa.boilerplate.palindrome.domain.ports

import com.serwa.boilerplate.palindrome.domain.model.PalindromeCreatedEvent

interface PalindromePublisher {
	fun publish(event: PalindromeCreatedEvent)
}
