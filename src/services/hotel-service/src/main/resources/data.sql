INSERT INTO countries (country_id, country) VALUES (1000, 'Country 1');
INSERT INTO countries (country_id, country) VALUES (2000, 'Country 2');

INSERT INTO cities (city_id, country_id, city) VALUES (1000, 1000, 'City 1');
INSERT INTO cities (city_id, country_id, city) VALUES (2000, 1000, 'City 2');
INSERT INTO cities (city_id, country_id, city) VALUES (3000, 2000, 'City 3');

INSERT INTO hotels (hotel_id, hotel_uid, rooms, cost, city_id, hotel_name, address) VALUES (1000, '9d05ba36-bf8d-11eb-8529-0242ac130003', 100, 2000, 1000, 'Hotel 1', 'Address 1');
INSERT INTO hotels (hotel_id, hotel_uid, rooms, cost, city_id, hotel_name, address) VALUES (2000, 'ab84f4e6-bf8d-11eb-8529-0242ac130003', 200, 3000, 2000, 'Hotel 2', 'Address 2');
INSERT INTO hotels (hotel_id, hotel_uid, rooms, cost, city_id, hotel_name, address) VALUES (3000, 'b0698102-bf8d-11eb-8529-0242ac130003', 250, 5000, 3000, 'Hotel 3', 'Address 3');