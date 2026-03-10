
-- INSERT INTO roles(id, name) VALUES (1,'ROLE_ADMIN');
-- INSERT INTO roles(id, name) VALUES (2,'ROLE_USER');
--
-- INSERT INTO users(id,username,password,email,enabled)
-- VALUES(1,'admin','$2a$10$W5C9R4S7BgMt1iHwoFe9pOxCgwqEW7lZ831FY4UrRjuq0bD2yVNxy','admin@gmail.com',1);
--
-- INSERT INTO user_roles(user_id, role_id) VALUES(1,1);

INSERT INTO roles(name) VALUES ('ROLE_ADMIN');
INSERT INTO roles(name) VALUES ('ROLE_USER');

INSERT INTO users(username,password,email,enabled)
VALUES('admin','$2a$10$W5C9R4S7BgMt1iHwoFe9pOxCgwqEW7lZ831FY4UrRjuq0bD2yVNxy','admin@gmail.com',1);

INSERT INTO user_roles(user_id, role_id) VALUES(1,1);

