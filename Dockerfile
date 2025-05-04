From openjdk:17-jdk-slim

WORKDIR /app

COPY target/seguimiento_envios-0.0.1-SNAPSHOT.jar app.jar

COPY Wallet_O30QTVJ7HXEFER8K /app/Wallet

EXPOSE 8080

ENTRYPOINT [ "java", "-jar", "app.jar" ]