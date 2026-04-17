# Build stage: requires network to download Maven dependencies
FROM maven:3.9.9-eclipse-temurin-11-alpine AS build
WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn -q -B clean package -DskipTests

# Runtime: JRE only
FROM eclipse-temurin:11-jre-alpine
WORKDIR /app

ENV JAVA_OPTS=""
COPY --from=build /app/target/tallerProxy-0.0.1-SNAPSHOT.jar /app/app.jar

EXPOSE 8080

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /app/app.jar"]
