# Stage 1: Сборка приложения
FROM maven:3.8.4-jdk-8 AS build

# Устанавливаем рабочую директорию
WORKDIR /app

# Копируем файлы сборки
COPY .mvn .mvn
COPY mvnw pom.xml ./
RUN mvn -B dependency:go-offline

# Копируем исходный код
COPY src ./src

# Собираем приложение
RUN mvn -B package -DskipTests

# Stage 2: Запуск приложения в отдельном контейнере
FROM openjdk:8-jdk-alpine

# Копируем собранное приложение из предыдущего стейджа
COPY --from=build /app/target/*.jar /app/app.jar
COPY wait-for-it.sh .

# Ожидаем, пока все необходимые сервисы будут доступны
RUN apk add --no-cache bash
RUN chmod +x wait-for-it.sh

# Установка переменных окружения
ENV SESSION_SERVICE_HOST=session-service
ENV SESSION_SERVICE_PORT=8081
ENV HOTEL_SERVICE_HOST=hotel-service
ENV HOTEL_SERVICE_PORT=8082
ENV BOOKING_SERVICE_HOST=booking-service
ENV BOOKING_SERVICE_PORT=8083
ENV PAYMENT_SERVICE_HOST=payment-service
ENV PAYMENT_SERVICE_PORT=8084
ENV LOYALTY_SERVICE_HOST=loyalty-service
ENV LOYALTY_SERVICE_PORT=8085
ENV REPORT_SERVICE_HOST=report-service
ENV REPORT_SERVICE_PORT=8086

# Запускаем приложение
CMD ["./wait-for-it.sh", "-t", "60", "session-service:8081", "--", \
     "./wait-for-it.sh", "-t", "60", "hotel-service:8082", "--", \
     "./wait-for-it.sh", "-t", "60", "booking-service:8083", "--", \
     "./wait-for-it.sh", "-t", "60", "payment-service:8084", "--", \
     "./wait-for-it.sh", "-t", "60", "loyalty-service:8085", "--", \
     "./wait-for-it.sh", "-t", "60", "report-service:8086", "--", \
     "java", "-jar", "/app/app.jar"]
