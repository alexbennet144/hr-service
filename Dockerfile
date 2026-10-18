# ===============================
# Stage 1: Build
# ===============================
FROM maven:3.9.9-eclipse-temurin-21 AS builder

WORKDIR /app

# Accept GitHub token at build time
ARG GITHUB_TOKEN
ENV GITHUB_TOKEN=${GITHUB_TOKEN}

# Create .m2 directory
RUN mkdir -p /root/.m2

# Copy Maven settings (contains ${env.GITHUB_TOKEN})
COPY settings.xml /root/.m2/settings.xml

COPY pom.xml .
RUN mvn dependency:go-offline

COPY src ./src
RUN mvn clean package -DskipTests


# ===============================
# Stage 2: Runtime
# ===============================
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

COPY --from=builder /app/target/hr-service-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENV JAVA_OPTS="-Xms256m -Xmx512m"

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
