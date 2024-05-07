INSERT INTO roles (role_id, role) VALUES (1, 'ROLE_USER');
INSERT INTO roles (role_id, role) VALUES (2, 'ROLE_ADMIN');

INSERT INTO users (id, name, user_uid, password, role_id) VALUES (1000, 'User', '6d2cb5a0-943c-4b96-9aa6-89eac7bdfd2b', '$2a$10$qgBcax6zQLYlWoK6R.cjDOGkH2xOjllmc7iEvBe1HIHt5ylZFo0b.', 1);
INSERT INTO users (id, name, user_uid, password, role_id) VALUES (2000, 'Admin', 'bb5af2ce-bf03-11eb-8529-0242ac130003', '$2a$10$qgBcax6zQLYlWoK6R.cjDOGkH2xOjllmc7iEvBe1HIHt5ylZFo0b.', 2);

