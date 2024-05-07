# Используем базовый образ Nginx
FROM nginx:latest

RUN apt-get update && apt-get install -y netcat-openbsd

COPY nginx.conf /etc/nginx/nginx.conf

COPY sites-enabled/proxy.conf /etc/nginx/sites-enabled/proxy.conf

COPY wait-for-services.sh /usr/local/bin/wait-for-services.sh

RUN chmod +x /usr/local/bin/wait-for-services.sh

CMD ["/usr/local/bin/wait-for-services.sh"]
