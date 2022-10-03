import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val ktlint: Configuration by configurations.creating

plugins {
	id("application")

	kotlin("jvm") version "1.7.10"
	kotlin("plugin.spring") version "1.6.10"

	id("org.springframework.boot") version "2.6.6"
	id("io.spring.dependency-management") version "1.0.11.RELEASE"

	id("groovy")
	id("com.coditory.integration-test") version "1.3.0"
}

group = "com.serwa.boilerplate"
version = "0.1.0"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
	mavenCentral()
}

sourceSets {
	create("architecture") {
		compileClasspath += sourceSets.test.get().output
		runtimeClasspath += sourceSets.test.get().output
	}
}

val architectureImplementation: Configuration by configurations.getting {
	extendsFrom(configurations.testImplementation.get())
}

val architectureRuntimeOnly: Configuration by configurations.getting {
	extendsFrom(configurations.testRuntimeOnly.get())
}

val architectureTest = task<Test>("architectureTest") {
	description = "Runs architecture tests."
	group = "verification"
	testClassesDirs = sourceSets["architecture"].output.classesDirs
	classpath = sourceSets["architecture"].runtimeClasspath
	shouldRunAfter("test")
}

tasks.check { dependsOn(architectureTest) }

kotlin.target.compilations.getByName("architecture") {
	associateWith(target.compilations.getByName("test"))
	associateWith(target.compilations.getByName("main"))
}

extra["springCloudVersion"] = "2021.0.4"

dependencyManagement {
	imports {
		mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
	}
}

val zalandoProblemVersion = "0.27.0"
val postgresqlVersion = "42.5.0"
val liquibaseVersion = "4.16.0"
val kotlinLoggingVersion = "2.1.23"
val logbackVersion = "1.2.11"
val groovyAllVersion = "3.0.12"
val spockBomVersion = "2.1-groovy-3.0"
val restitoVersion = "1.0.0"
val testContainersVersion = "1.17.3"
val jsonPathAssertionVersion = "2.7.0"
val ktlintVersion = "0.45.2"
val archunitVersion = "0.17.0"

dependencies {
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-validation")

	implementation("org.springframework.cloud:spring-cloud-starter-openfeign")
	implementation("org.zalando:problem-spring-web:$zalandoProblemVersion")

	implementation("org.postgresql:postgresql:$postgresqlVersion")
	implementation("org.liquibase:liquibase-core:$liquibaseVersion")

	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

	implementation("io.github.microutils:kotlin-logging-jvm:$kotlinLoggingVersion")
	implementation("ch.qos.logback:logback-classic:$logbackVersion")

	developmentOnly("org.springframework.boot:spring-boot-devtools")

	testImplementation("org.codehaus.groovy:groovy-all:$groovyAllVersion")
	testImplementation(platform("org.spockframework:spock-bom:$spockBomVersion"))
	testImplementation("org.spockframework:spock-core")

	integrationImplementation("org.springframework.boot:spring-boot-starter-test")
	integrationImplementation("org.spockframework:spock-spring")

	integrationImplementation(platform("org.testcontainers:testcontainers-bom:$testContainersVersion")) // todo dziala?
	integrationImplementation("org.testcontainers:postgresql:")

	integrationImplementation("com.xebialabs.restito:restito:$restitoVersion")
	integrationImplementation("com.jayway.jsonpath:json-path:$jsonPathAssertionVersion")

	architectureImplementation("com.tngtech.archunit:archunit:$archunitVersion")

	ktlint("com.pinterest:ktlint:$ktlintVersion") {
		attributes {
			attribute(Bundling.BUNDLING_ATTRIBUTE, objects.named(Bundling.EXTERNAL))
		}
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}


tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "11"
	}
}

val ktlintOutputDir = "${project.buildDir}/reports/ktlint/"
val ktlintInputFiles = project.fileTree(mapOf("dir" to "src", "include" to "**/*.kt"))

val ktlintCheck by tasks.creating(JavaExec::class) {
	inputs.files(ktlintInputFiles)
	outputs.dir(ktlintOutputDir)

	description = "Check Kotlin code style."
	classpath = ktlint
	mainClass.set("com.pinterest.ktlint.Main")
	args = listOf("src/**/*.kt")
}

val ktlintFormat by tasks.creating(JavaExec::class) {
	inputs.files(ktlintInputFiles)
	outputs.dir(ktlintOutputDir)

	description = "Fix Kotlin code style deviations."
	classpath = ktlint
	mainClass.set("com.pinterest.ktlint.Main")
	args = listOf("-F", "src/**/*.kt")
}
