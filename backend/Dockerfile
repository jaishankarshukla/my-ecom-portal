# ===== Stage 1: Build the app =====
FROM eclipse-temurin:25-jdk AS builder

# Set working directory
WORKDIR /app

# Copy Maven wrapper or pom.xml first for caching
COPY pom.xml mvnw ./
COPY .mvn .mvn
RUN chmod +x mvnw

RUN ./mvnw dependency:go-offline

# Copy source code
COPY src src

# Build the project
RUN ./mvnw clean package -DskipTests

# ===== Stage 2: Run the app =====
FROM eclipse-temurin:25-jre

WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]
