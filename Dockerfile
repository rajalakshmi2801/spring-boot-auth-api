FROM eclipse-temurin:17-jdk

WORKDIR /app

COPY . .

# Give execution permission to mvnw
RUN chmod +x mvnw

# Build the project
RUN ./mvnw clean package -DskipTests

EXPOSE 8087

CMD ["java", "-jar", "target/demo-0.0.1-SNAPSHOT.jar"]
