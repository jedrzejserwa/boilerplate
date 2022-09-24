package com.serwa.boilerplate.palindrome.domain.ports

import com.serwa.boilerplate.palindrome.domain.model.Palindrome
import com.serwa.boilerplate.palindrome.domain.model.Word

interface PalindromeRepository {
	fun palindrome(word: Word): Palindrome?
	fun store(palindrome: Palindrome)
}
