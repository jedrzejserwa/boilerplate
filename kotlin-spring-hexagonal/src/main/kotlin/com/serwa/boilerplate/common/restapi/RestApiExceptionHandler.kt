package com.serwa.boilerplate.common.restapi

import mu.KotlinLogging
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.ServletWebRequest
import org.springframework.web.context.request.WebRequest
import org.zalando.problem.Problem
import org.zalando.problem.Status

@RestControllerAdvice
class RestApiExceptionHandler {

	private val logger = KotlinLogging.logger {}

	@ExceptionHandler(Exception::class)
	fun handle(webRequest: WebRequest, exception: Exception): ResponseEntity<Problem> {
		logger.error(
			"Caught unhandled exception during processing {} request",
			(webRequest as ServletWebRequest).request.requestURI.toString(),
			exception
		)

		return ResponseEntity.internalServerError().body(
			Problem.valueOf(Status.INTERNAL_SERVER_ERROR, "Internal server error")
		)
	}

	@ExceptionHandler(IllegalArgumentException::class)
	fun handle(exception: IllegalArgumentException): ResponseEntity<Problem> {
		return ResponseEntity.badRequest().body(
			Problem.valueOf(Status.BAD_REQUEST, exception.message)
		)
	}
}
