# Rest API 

- Maven project
- Spring Boot, Spring Data, Spring Web, Spring Security
- OAuth2 authentication
- Swagger
- MySQL
- Log4j
- Lombock

See `application.properties` for general configuration

---

SWagger UI

    localhost/swagger-ui.html

Spring Health check

    localhost/actuator/health


Run application

    mvn spring-boot:run [-Pprd]

Generate executable JARs

    mvn package spring-boot:repackage [-Pprd]