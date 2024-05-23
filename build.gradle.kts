plugins {
	java
	id("org.springframework.boot") version "3.2.4"
	id("io.spring.dependency-management") version "1.1.4"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_17
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	compileOnly("org.projectlombok:lombok")
	developmentOnly("org.springframework.boot:spring-boot-devtools")
	runtimeOnly("com.mysql:mysql-connector-j")
	runtimeOnly("com.h2database:h2")
	annotationProcessor("org.projectlombok:lombok")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	implementation ("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation ("org.springframework.boot:spring-boot-starter-thymeleaf")
	implementation("org.springframework.boot:spring-boot-starter-validation:3.0.5")
	implementation("com.fasterxml.jackson.core:jackson-core:2.17.1")
	implementation("com.fasterxml.jackson.core:jackson-databind:2.17.1")
	implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.17.1")
	implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-xml:2.17.1")
	implementation("com.rabbitmq:amqp-client:5.21.0")
	implementation("com.fasterxml.jackson.core:jackson-annotations:2.17.1")
	implementation("org.springframework.boot:spring-boot-starter-security")

	implementation("org.yaml:snakeyaml")
	implementation("com.github.javafaker:javafaker:1.0.2") { exclude ("org.yaml") }
	implementation("nz.net.ultraq.thymeleaf:thymeleaf-layout-dialect")
	implementation("org.modelmapper:modelmapper:3.2.0")

	implementation("org.thymeleaf.extras:thymeleaf-extras-springsecurity6")
//	implementation("org.springframework.boot:spring-boot-starter-validation")
//	implementation("com.fasterxml.jackson.core:jackson-databind")
//	implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")
//	implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-xml")
//	implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-csv")
//	implementation("com.rabbitmq:amqp-client")
//      https://mvnrepository.com/artifact/org.modelmapper/modelmapper

}

tasks.withType<Test> {
	useJUnitPlatform()
}

val integrationTestTask = tasks.register<Test>("integrationTest") {
	group = "verification"
	filter {
		includeTestsMatching("*IT") //Justera denna ev beroende på namning av testclasser.
	}
}

tasks.test{
	filter{
		includeTestsMatching("*Test") //Justera denna ev beroende på namning av testclasser.

	}
}
