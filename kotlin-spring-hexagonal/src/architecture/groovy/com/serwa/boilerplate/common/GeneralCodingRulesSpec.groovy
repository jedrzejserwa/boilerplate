package com.serwa.boilerplate.common

import com.serwa.boilerplate.BaseArchUnitSpec

import static com.tngtech.archunit.library.GeneralCodingRules.*

class GeneralCodingRulesSpec extends BaseArchUnitSpec {

	def "Simple coding rules"() {
		expect:
			rules.each { check(it) }

		where:
			rules << [NO_CLASSES_SHOULD_ACCESS_STANDARD_STREAMS,
					  NO_CLASSES_SHOULD_THROW_GENERIC_EXCEPTIONS,
					  NO_CLASSES_SHOULD_USE_JAVA_UTIL_LOGGING,
					  NO_CLASSES_SHOULD_USE_JODATIME,
					  NO_CLASSES_SHOULD_USE_FIELD_INJECTION,
//				fields().that().haveRawType(Logger.class)
//					.should().bePrivate()
//					.andShould().beStatic()
//					.andShould().beFinal()
			]
	}
}
