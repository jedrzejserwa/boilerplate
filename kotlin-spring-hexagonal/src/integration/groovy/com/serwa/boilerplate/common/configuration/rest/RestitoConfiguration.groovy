package com.serwa.boilerplate.common.configuration.rest

import com.xebialabs.restito.server.StubServer
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.boot.test.util.TestPropertyValues
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.util.SocketUtils

@TestConfiguration
class RestitoConfiguration {

	private static final StubServer stubServer

	private static final int stubServerPort

	static {
		stubServerPort = getRandomPort()
		stubServer = new StubServer(stubServerPort).run()
	}

	static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

		void initialize(ConfigurableApplicationContext configurableApplicationContext) {
			TestPropertyValues.of(
				"feign.nbpApi.url=http://127.0.0.1:$stubServerPort",
			).applyTo(configurableApplicationContext.getEnvironment())
		}
	}

	static void cleanup() {
		stubServer.clear()
	}

	static StubServer getStubServer() {
		assert stubServer != null

		return stubServer
	}

	private static int getRandomPort() {
		int port = SocketUtils.findAvailableTcpPort()

		try (ServerSocket serverSocket = new ServerSocket(port)) {
			assert serverSocket != null
			assert serverSocket.getLocalPort() == port
		} catch (IOException e) {
			throw new Error("Port is not available", e)
		}

		return port
	}
}
