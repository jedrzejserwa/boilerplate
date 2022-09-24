package com.serwa.boilerplate.palindrome.infrastructure.adapters.restapi

import com.serwa.boilerplate.common.restapi.RestApiVersioning.API_VERSION_V1
import com.serwa.boilerplate.palindrome.application.PalindromeFacade
import com.serwa.boilerplate.palindrome.application.PalindromeResult
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/palindrome")
class PalindromeEndpoint(private val palindromeFacade: PalindromeFacade) {

	@PutMapping(headers = [API_VERSION_V1])
	fun createPalindrome(@RequestBody createPalindromeRequest: CreatePalindromeRequest): PalindromeResult {
		return palindromeFacade.handle(createPalindromeRequest)
	}
}

data class CreatePalindromeRequest(val word: String)
