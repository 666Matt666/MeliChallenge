# ---------------------------
# STAGE 1: Build
# ---------------------------
FROM maven:3.9.9-eclipse-temurin-17 AS build

WORKDIR /app

# Copiamos solo el pom.xml primero para aprovechar la cache de dependencias
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Ahora copiamos el resto del código fuente
COPY src ./src

# Empaquetamos el JAR
RUN mvn clean package -DskipTests

# ---------------------------
# STAGE 2: Runtime
# ---------------------------
FROM eclipse-temurin:17-jdk-jammy

# Crear un usuario no-root por seguridad
RUN addgroup --system spring && adduser --system spring --ingroup spring

WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

USER spring

EXPOSE 8080

# Opción 1: Config estándar
ENTRYPOINT ["java", "-jar", "app.jar"]

# Opción 2 (más pro): Permitir flags de memoria dinámicos
# ENTRYPOINT ["java", "-XX:+UseContainerSupport", "-XX:MaxRAMPercentage=75.0", "-jar", "app.jar"]