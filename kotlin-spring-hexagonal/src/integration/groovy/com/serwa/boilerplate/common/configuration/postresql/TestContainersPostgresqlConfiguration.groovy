package com.serwa.boilerplate.common.configuration.postresql

import org.springframework.boot.test.context.TestConfiguration
import org.springframework.boot.test.util.TestPropertyValues
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.testcontainers.containers.PostgreSQLContainer

@TestConfiguration
class TestContainersPostgresqlConfiguration {

	private static final PostgreSQLContainer postgreSQLContainer

	static {
		postgreSQLContainer = new PostgreSQLContainer("postgres:14.2")
			.withDatabaseName("boilerplate-${UUID.randomUUID()}")
			.withUsername("postgres")
			.withPassword("postgres")

		postgreSQLContainer.start()
	}

	static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

		void initialize(ConfigurableApplicationContext configurableApplicationContext) {
			TestPropertyValues.of(
				"spring.datasource.url=" + postgreSQLContainer.getJdbcUrl(),
				"spring.datasource.username=" + postgreSQLContainer.getUsername(),
				"spring.datasource.password=" + postgreSQLContainer.getPassword()
			).applyTo(configurableApplicationContext.getEnvironment())
		}
	}
}
