#!/bin/bash

# Функция для проверки доступности сервиса
wait_for_service() {
    local service="$1"
    local host="$2"
    local port="$3"
    local timeout="$4"
    
    until nc -z "$host" "$port"; do
        >&2 echo "$service is unavailable - sleeping"
        sleep 1
        timeout=$((timeout - 1))
        if [ "$timeout" -eq 0 ]; then
            >&2 echo "Timeout reached, $service is still unavailable"
            exit 1
        fi
    done
    >&2 echo "$service is available"
}

wait_for_service "gateway-service" "gateway-service" "8087" "60"

wait_for_service "session-service" "session-service" "8081" "60"

# Запуск Nginx
echo "Starting Nginx..."
exec nginx -g "daemon off;"
