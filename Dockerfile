FROM eclipse-temurin:21-jdk
RUN apt-get update && apt-get install -y curl

WORKDIR /app

COPY . .

RUN chmod +x ./gradlew

RUN ./gradlew build -x test

EXPOSE 8080

CMD ["java", "-jar", "build/libs/app.jar"]