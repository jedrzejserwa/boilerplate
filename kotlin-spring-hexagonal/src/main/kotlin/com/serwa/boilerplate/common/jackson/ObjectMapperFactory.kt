package com.serwa.boilerplate.common.jackson

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.databind.cfg.ConstructorDetector
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinFeature
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule
import java.math.BigDecimal

class ObjectMapperFactory {

	companion object {

		@JvmStatic
		fun create(): ObjectMapper {
			val kotlinModule = KotlinModule.Builder()
				.withReflectionCacheSize(512)
				.configure(KotlinFeature.NullToEmptyCollection, false)
				.configure(KotlinFeature.NullToEmptyMap, false)
				.configure(KotlinFeature.NullIsSameAsDefault, false)
				.configure(KotlinFeature.SingletonSupport, false)
				.configure(KotlinFeature.StrictNullChecks, false)
				.build()

			return ObjectMapper()
				.registerModule(customSerializersModule())
				.registerModule(customDeserializersModule())
				.registerModule(JavaTimeModule())
				.registerModule(Jdk8Module())
				.registerModule(ParameterNamesModule())
				.registerModule(kotlinModule)
				.setConstructorDetector(ConstructorDetector.USE_PROPERTIES_BASED)
				.enable(DeserializationFeature.READ_ENUMS_USING_TO_STRING)
				.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS)
				.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
				.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
		}

		private fun customSerializersModule(): SimpleModule {
			val simpleModule = SimpleModule("boilerplate-jackson-custom-serializers")
			simpleModule.addSerializer(BigDecimal::class.java, ToStringSerializer())
			return simpleModule
		}

		private fun customDeserializersModule(): SimpleModule {
			return SimpleModule("boilerplate-jackson-custom-deserializers")
		}
	}
}
