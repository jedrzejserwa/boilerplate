package com.serwa.boilerplate

class BoilerplateApplicationIT extends IntegrationSpec {

	def "context loads"() {
		expect:
			applicationContext != null
	}
}
