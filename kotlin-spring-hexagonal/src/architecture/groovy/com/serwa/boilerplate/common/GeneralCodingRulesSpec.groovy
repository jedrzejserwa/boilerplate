package com.serwa.boilerplate.common

import com.serwa.boilerplate.BaseArchUnitSpec

import java.util.logging.Logger

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.fields
import static com.tngtech.archunit.library.GeneralCodingRules.NO_CLASSES_SHOULD_ACCESS_STANDARD_STREAMS
import static com.tngtech.archunit.library.GeneralCodingRules.NO_CLASSES_SHOULD_THROW_GENERIC_EXCEPTIONS
import static com.tngtech.archunit.library.GeneralCodingRules.NO_CLASSES_SHOULD_USE_JAVA_UTIL_LOGGING
import static com.tngtech.archunit.library.GeneralCodingRules.NO_CLASSES_SHOULD_USE_JODATIME
import static com.tngtech.archunit.library.GeneralCodingRules.NO_CLASSES_SHOULD_USE_FIELD_INJECTION

class GeneralCodingRulesSpec extends BaseArchUnitSpec {

    def "Simple coding rules"() {
        expect:
            rules.each { check(it) }

        where:
            rules << [
                    NO_CLASSES_SHOULD_ACCESS_STANDARD_STREAMS,
                    NO_CLASSES_SHOULD_THROW_GENERIC_EXCEPTIONS,
                    NO_CLASSES_SHOULD_USE_JAVA_UTIL_LOGGING,
                    NO_CLASSES_SHOULD_USE_JODATIME,
                    NO_CLASSES_SHOULD_USE_FIELD_INJECTION,
                    fields().that().haveRawType(Logger.class)
                            .should().bePrivate()
                            .andShould().beStatic()
                            .andShould().beFinal()
            ]
    }
}
