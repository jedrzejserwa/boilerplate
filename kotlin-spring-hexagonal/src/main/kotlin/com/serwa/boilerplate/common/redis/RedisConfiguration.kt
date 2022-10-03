package com.serwa.boilerplate.common.redis

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer

@Configuration
@EnableRedisRepositories
class RedisConfiguration {

    @Bean
    fun redisTemplate(
        redisConnectionFactory: RedisConnectionFactory,
        objectMapper: ObjectMapper
    ): RedisTemplate<Any, Any> {
        val template: RedisTemplate<Any, Any> = RedisTemplate()
        template.setConnectionFactory(redisConnectionFactory)
        template.valueSerializer = GenericJackson2JsonRedisSerializer(objectMapper)

        return template
    }
}
