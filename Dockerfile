FROM openjdk:25-ea-24-oracle
WORKDIR /app

COPY Wallet_MIBD/ /app/oracle_wallet/
COPY target/*.jar app.jar

EXPOSE 8080
CMD ["java", "-jar", "app.jar"]