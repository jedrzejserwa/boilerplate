package com.serwa.boilerplate.common.configuration.redis

import org.springframework.boot.test.context.TestConfiguration
import org.springframework.boot.test.util.TestPropertyValues
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.testcontainers.containers.GenericContainer
import org.testcontainers.utility.DockerImageName

@TestConfiguration
class TestContainersRedisConfiguration {

    private static final GenericContainer redisContainer

    static {
        redisContainer = new GenericContainer(DockerImageName.parse("redis:6.2.6-alpine"))
                .withExposedPorts(6379)

        redisContainer.start()
    }

    static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

        void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            TestPropertyValues.of(
                    "spring.redis.host=" + redisContainer.getHost(),
                    "spring.redis.port=" + redisContainer.getFirstMappedPort()
            ).applyTo(configurableApplicationContext.getEnvironment())
        }
    }
}
