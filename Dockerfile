##############################################
# Stage 1: Build the application with Maven
##############################################
FROM maven:3.9-eclipse-temurin-17 AS build

WORKDIR /app

# Copy dependency descriptors first for layer caching
COPY backend/pom.xml ./pom.xml
RUN mvn dependency:go-offline -B

# Copy source code and build
COPY backend/src ./src
RUN mvn clean package -DskipTests -B

##############################################
# Stage 2: Runtime image with minimal JRE
##############################################
FROM eclipse-temurin:17-jre-alpine

RUN addgroup -S appgroup && adduser -S appuser -G appgroup

WORKDIR /app

# Copy the built JAR from the build stage
COPY --from=build /app/target/*.jar app.jar

# Create directories for logs and temp files
RUN mkdir -p /app/logs /app/tmp && \
    chown -R appuser:appgroup /app

USER appuser

# JVM tuning for containerized environments
ENV JAVA_OPTS="-XX:+UseContainerSupport \
    -XX:MaxRAMPercentage=75.0 \
    -XX:InitialRAMPercentage=50.0 \
    -Djava.security.egd=file:/dev/./urandom \
    -Djava.io.tmpdir=/app/tmp"

EXPOSE 8080

HEALTHCHECK --interval=30s --timeout=5s --start-period=40s --retries=3 \
    CMD wget --no-verbose --tries=1 --spider http://localhost:8080/actuator/health || exit 1

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
