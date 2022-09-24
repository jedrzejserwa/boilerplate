package com.serwa.boilerplate.palindrome.domain.model

data class PalindromeCreatedEvent(val source: Word, val result: Word, val createdAt: Timestamp)
