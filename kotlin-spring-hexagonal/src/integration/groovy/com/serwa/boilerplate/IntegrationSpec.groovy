package com.serwa.boilerplate

import com.serwa.boilerplate.common.configuration.time.FixedTimeConfiguration
import groovy.json.JsonBuilder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification

import javax.transaction.Transactional

@Transactional
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles(profiles = ["integration"])
@ContextConfiguration(
	classes = [
		FixedTimeConfiguration.class
	],
	initializers = [

	]
)
abstract class IntegrationSpec extends Specification {

	@Autowired
	protected ApplicationContext applicationContext

	@Autowired
	protected MockMvc mvc

	protected String asJson(Map map) {
		return new JsonBuilder(map).toPrettyString()
	}
}
