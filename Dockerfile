FROM gcr.io/distroless/java21-debian12

MAINTAINER kenuki.dev

EXPOSE 8080

USER nonroot

WORKDIR /app

COPY build/libs/*.jar app.jar

ENTRYPOINT ["java", "-jar", "/app/app.jar"]