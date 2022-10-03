package com.serwa.boilerplate.common.adapters.rest

import com.fasterxml.jackson.databind.ObjectMapper
import com.serwa.boilerplate.common.jackson.ObjectMapperConfiguration
import com.xebialabs.restito.semantics.Action
import com.xebialabs.restito.server.StubServer

import static com.xebialabs.restito.semantics.Action.stringContent

interface RestitoAware {
	StubServer stubServer()

	static final ObjectMapper mapper = new ObjectMapperConfiguration().objectMapper()

	default Action jsonResponse(Object object) {
		return stringContent(mapper.writeValueAsString(object))
	}
}
