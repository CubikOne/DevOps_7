\c postgres;
DROP DATABASE IF EXISTS users_db;
CREATE DATABASE users_db OWNER postgres;

DROP DATABASE IF EXISTS hotels_db;
CREATE DATABASE hotels_db OWNER postgres;

DROP DATABASE IF EXISTS reservations_db;
CREATE DATABASE reservations_db OWNER postgres;

DROP DATABASE IF EXISTS payments_db;
CREATE DATABASE payments_db OWNER postgres;

DROP DATABASE IF EXISTS balances_db;
CREATE DATABASE balances_db OWNER postgres;

DROP DATABASE IF EXISTS statistics_db;
CREATE DATABASE statistics_db OWNER postgres;