
#ONLY RUN
FROM openjdk:8-jre-slim
WORKDIR /app
COPY ./target/*.jar ./app.jar

# BUILD AND RUN
# FROM maven:3.5.2-jdk-8-alpine AS MAVEN_BUILD
# WORKDIR /build
# COPY . ./
# RUN mvn dependency:go-offline package spring-boot:repackage -Pprd --quiet -DskipTests

# FROM openjdk:8-jre-slim
# WORKDIR /app
# COPY --from=MAVEN_BUILD /build/target/*.jar ./app.jar

# COMMOM 
ENTRYPOINT ["java", "-jar", "app.jar"]