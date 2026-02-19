FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

COPY pom.xml .
COPY mvnw .
COPY mvnw.cmd .
COPY .mvn .mvn

RUN chmod +x mvnw
RUN ./mvnw dependency:go-offline

COPY src src

# Construye el JAR
RUN ./mvnw clean package -DskipTests

# Copiamos el JAR con nombre fijo
RUN cp target/*jar app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]
