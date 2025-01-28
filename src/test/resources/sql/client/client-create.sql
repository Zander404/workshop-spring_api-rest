INSERT INTO users (id, username, password, role) VALUES (100, 'ana@gmail.com', '$2a$12$hU1TMk4F7qQtuogYvpqSneNOe0MgUppHGFPAVAbl0Yrjuo4gQC...', 'ROLE_ADMIN');
INSERT INTO users (id, username, password, role) VALUES (101, 'bia@gmail.com', '$2a$12$hU1TMk4F7qQtuogYvpqSneNOe0MgUppHGFPAVAbl0Yrjuo4gQC...', 'ROLE_USER');
INSERT INTO users (id, username, password, role) VALUES (102, 'bob@gmail.com', '$2a$12$hU1TMk4F7qQtuogYvpqSneNOe0MgUppHGFPAVAbl0Yrjuo4gQC...', 'ROLE_ADMIN');
INSERT INTO users (id, username, password, role) VALUES (103, 'toby@gmail.com', '$2a$12$hU1TMk4F7qQtuogYvpqSneNOe0MgUppHGFPAVAbl0Yrjuo4gQC...', 'ROLE_USER');

INSERT INTO clients (id, name, cpf , id_user) VALUES (1000, 'Bianca Silva', '78272954010', 101);
INSERT INTO clients (id, name, cpf , id_user) VALUES (1001, 'Roberto Gomes', '36619296076', 102);
INSERT INTO clients (id, name, cpf , id_user) VALUES (1002, 'Bianco Silva', '32402301007', 103);