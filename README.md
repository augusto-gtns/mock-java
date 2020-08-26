# Mock Application

SWagger UI

curl -XGET -s localhost/swagger-ui.html
curl -XGET -s localhost/v2/api-docs

Spring Health check

curl -XGET -s localhost/actuator/health

### Maven

Run application

    mvn spring-boot:run [-Pprd]

Generate executable JARs

    mvn package spring-boot:repackage [-Pprd]
