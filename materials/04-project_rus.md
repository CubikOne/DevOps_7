# Пример мультисервисного приложения


В папке `./src/` можно найти пример мультисервисного приложения. Ниже представлена его концептуальная схема.


<img src="misc/images/project_diagram.png"  width="800">


Проект написан на языке java (jdk8), но знать его для разворачивания в docker-контейнерах совсем не обязательно. Достаточно лишь знать какие для него нужны зависимости и как отдельные сервисы соотносятся друг с другом.


Итак, само приложение представляет из себя сервис по бронированию номеров. Вернее его backend. Состоит этот backend из девяти частей (сервисов в терминологии docker-compose):


1. База данных postgres.
2. Очередь сообщений rabbitmq.
3. Session service - сервис управляющий сессиями пользователей.
4. Hotel service - сервис управляющий сущностью отелей.
5. Payment service - сервис управляющий оплатой.
6. Loyalty service - сервис управляющий программой лояльности.
7. Report service - сервис осуществляющий сбор статистики.
8. Booking service - сервис управляющий резервированием.
9. Gateway service - фасад для взаимодействия с остальными микросервисами


Начнем с двух первых сервисов. Для rabbitmq лучше всего использовать стандартный образ, так как никакой дополнительной настройки не потребуется (например - rabbitmq:3-management-alpine).


Популяция начальными значениями баз данных postgres производится соответствующими сервисами автоматически, но сами базы данных надо создать, запустив скрипт `src\services\database\init.sql`


В случае с остальными же сервисами, придется поработать немного больше. Тут есть несколько вариантов, но мы рассмотрим только два из них.


## 1. Локальная сборка jar-пакетов


Приложения написанные на java исполняются в специальной виртуальной машине - jvm. Сами же приложения в собранном виде представляют собой пакеты - jar-файлы, содержащие в себе сам код программы и все необходимые зависимости. Таким образом, в качестве самого тривиального запуска java программы будет локальная сборка проекта (в данном случае используется менеджер пакетов maven и его обертка mvnw, находящаяся в папке с каждым сервсом) путем выполнения команды `./mvnw package -DskipTests`. Первая сборка первого проекта может занять продолжительное время. После этого полученный jar-файл в сгенерированной папке target и будет являться тем самым исполняемым файлом.


Теперь в Dockerfile на базе, например, `opendjdk-:8-jdk-alpine`, достаточно указать инструкции копирующие собранный проект и запускающие его командой `java -jar target/*.jar`.


P.S.: Важно не забыть здесь, что большинство сервисов для корректного старта требуют уже развернутый сервис с postgres, таким образом не стоит забывать о `wait-for-it.sh` скрипте.


P.P.S.: Важно указывать точный тег базового образа чтобы избежать использования часто меняющегося тега `latest`. *Публичные широко используемые образы не меняются.*


## 2. Сборка внутри docker


Проблемы предыдущего подхода очевидны, если вы попробуете этот вариант. Слишком много ручной работы. Поэтому переходим к сборке внутри docker.


Для этого можно создать рабочую директорию внутри образа и переместить туда все файлы необходимые для сборки, а затем уже собрать.


Более того, менеджер maven поддерживает отдельное подключение зависимостей, которое и является самым долгим процессом в сборке (отделение этого этапа от сборки является оптимизацией исходя из природы слоев docker-образа). Все зависимости содержаться в отдельном файле - `pom.xml`, поэтому план на новый Dockerfile следующий:


1. Создать рабочую директорию.
2. Перенести враппер maven и `pom.xml`.
3. Выполнить установку зависимостей проекта командой `./mvnw dependency:go-offline`.
4. Скопировать исходный код проекта
5. Собрать проект аналогично предыдущему подходу или же запустить его с помощью `./mvnw spring-boot:run`


P.S.: чтобы сократить объем финального образа можно применить подход с использованием отдельного образа для сборки (multi-stage build), так как далеко не все файлы используемые при сборке требуются при запуске.


## Что нужно учесть


Сервисы на джаве ожидают некоторый набор переменных окружения:


### Session service


- POSTGRES_HOST: <хост бд>
- POSTGRES_PORT: 5432
- POSTGRES_USER : postgres (может отличаться)
- POSTGRES_PASSWORD: "postgres" (может отличаться)
- POSTGRES_DB: users_db




### Hotel service


- POSTGRES_HOST: <хост бд>
- POSTGRES_PORT: 5432
- POSTGRES_USER : postgres (может отличаться)
- POSTGRES_PASSWORD: "postgres" (может отличаться)
- POSTGRES_DB: hotels_db


### Payment service


- POSTGRES_HOST: <хост бд>
- POSTGRES_PORT: 5432
- POSTGRES_USER : postgres (может отличаться)
- POSTGRES_PASSWORD: "postgres" (может отличаться)
- POSTGRES_DB: payments_db


### Loyalty service


- POSTGRES_HOST: <хост бд>
- POSTGRES_PORT: 5432
- POSTGRES_USER : postgres (может отличаться)
- POSTGRES_PASSWORD: "postgres" (может отличаться)
- POSTGRES_DB: balances_db


### Report service


- POSTGRES_HOST: <хост бд>
- POSTGRES_PORT: 5432
- POSTGRES_USER : postgres (может отличаться)
- POSTGRES_PASSWORD: "postgres" (может отличаться)
- POSTGRES_DB: statistics_db
- RABBIT_MQ_HOST: <хост очереди>
- RABBIT_MQ_PORT: 5672
- RABBIT_MQ_USER: guest
- RABBIT_MQ_PASSWORD: guest
- RABBIT_MQ_QUEUE_NAME: messagequeue
- RABBIT_MQ_EXCHANGE: messagequeue-exchange


### Booking service


- POSTGRES_HOST: <хост бд>
- POSTGRES_PORT: 5432
- POSTGRES_USER : postgres (может отличаться)
- POSTGRES_PASSWORD: "postgres" (может отличаться)
- POSTGRES_DB: reservations_db
- RABBIT_MQ_HOST: <хост очереди>
- RABBIT_MQ_PORT: 5672
- RABBIT_MQ_USER: guest
- RABBIT_MQ_PASSWORD: guest
- RABBIT_MQ_QUEUE_NAME: messagequeue
- RABBIT_MQ_EXCHANGE: messagequeue-exchange
- HOTEL_SERVICE_HOST: <хост hotel service>
- HOTEL_SERVICE_PORT: 8082
- PAYMENT_SERVICE_HOST: <хост payment service>
- PAYMENT_SERVICE_PORT: 8084
- LOYALTY_SERVICE_HOST: <хост loyalty service>
- LOYALTY_SERVICE_PORT: 8085


### Gateway service


- SESSION_SERVICE_HOST: <хост session service>
- SESSION_SERVICE_PORT: 8081
- HOTEL_SERVICE_HOST: <хост hotel service>
- HOTEL_SERVICE_PORT: 8082
- BOOKING_SERVICE_HOST: <хост booking service>
- BOOKING_SERVICE_PORT: 8083
- PAYMENT_SERVICE_HOST: <хост payment service>
- PAYMENT_SERVICE_PORT: 8084
- LOYALTY_SERVICE_HOST: <хост loyalty service>
- LOYALTY_SERVICE_PORT: 8085
- REPORT_SERVICE_HOST: <хост report service>
- REPORT_SERVICE_PORT: 8086


Сервисы открыты на соответствующих локальных портах:


- Session service - 8081
- Hotel service - 8082
- Booking service - 8083
- Payment service - 8084
- Loyalty service - 8085
- Report service - 8086
- Gateway service - 8087



