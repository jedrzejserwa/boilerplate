package com.serwa.boilerplate.palindrome

import com.tngtech.archunit.lang.ArchRule
import com.serwa.boilerplate.BaseArchUnitSpec

import static com.tngtech.archunit.library.Architectures.layeredArchitecture

class PalindromeModuleRules extends BaseArchUnitSpec {

	def "ports and adapters palindrome trading"() {
		given:
			ArchRule rule = layeredArchitecture()
				.layer("Application").definedBy("com.serwa.boilerplate.palindrome.application..")
				.layer("Domain").definedBy("com.serwa.boilerplate.palindrome.domain..")
				.layer("Domain Ports").definedBy("com.serwa.boilerplate.palindrome.domain.ports..")
				.layer("Infrastructure Adapters").definedBy("com.serwa.boilerplate.palindrome.infrastructure.adapters..")
				.layer("Infrastructure Config").definedBy("com.serwa.boilerplate.palindrome.infrastructure.configuration..")

				.whereLayer("Application").mayOnlyBeAccessedByLayers(
				"Infrastructure Adapters",
				"Infrastructure Config"
			)
				.whereLayer("Domain Ports").mayOnlyBeAccessedByLayers(
				"Domain",
				"Application",
				"Infrastructure Adapters",
				"Infrastructure Config",
			)
				.whereLayer("Infrastructure Config")
				.mayOnlyBeAccessedByLayers(
					"Infrastructure Adapters"
				)
		expect:
			check(rule)
	}
}
