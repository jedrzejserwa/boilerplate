package com.serwa.boilerplate.specs

import com.serwa.boilerplate.IntegrationSpec
import org.hamcrest.Matchers
import org.springframework.http.MediaType

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class PalindromeSpec extends IntegrationSpec {

	def "should create palindrome"() {
		expect:
			mvc.perform(put("/palindrome")
				.contentType(MediaType.APPLICATION_JSON)
				.header("X-API-VERSION", "v1")
				.content(asJson(
					[
						word: "test"
					]
				))
			).andExpect(status().isOk())
				.andExpect(jsonPath('$.source', Matchers.is("test")))
				.andExpect(jsonPath('$.result', Matchers.is("tset")))
	}

	def "should not create palindrome from empty string"() {
		expect:
			mvc.perform(put("/palindrome")
				.contentType(MediaType.APPLICATION_JSON)
				.header("X-API-VERSION", "v1")
				.content(asJson(
					[
						word: "   "
					]
				))
			).andExpect(status().isBadRequest())
				.andExpect(jsonPath('$.status', Matchers.is("BAD_REQUEST")))
				.andExpect(jsonPath('$.detail', Matchers.is("Text must be not blank")))
	}
}
