# 1) Build stage: compile & package into a fat jar
FROM maven:3.9.2-eclipse-temurin-17 AS builder
WORKDIR /workspace

# Cache dependencies
COPY pom.xml mvnw .mvn/ ./
RUN chmod +x mvnw && ./mvnw dependency:go-offline -B

# Copy source and build
COPY src ./src
RUN ./mvnw package -DskipTests -B

# 2) Runtime stage: minimal JRE to run your app
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Copy fat jar from build stage
COPY --from=builder /workspace/target/*.jar app.jar

# Expose port 8080 (default Spring Boot port)
EXPOSE 8080

# Run the jar
ENTRYPOINT ["java","-jar","app.jar"]