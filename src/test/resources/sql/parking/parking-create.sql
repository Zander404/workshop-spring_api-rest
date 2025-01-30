
INSERT INTO users (id, username, password, role) VALUES (100, 'ana@gmail.com', '$2a$12$hU1TMk4F7qQtuogYvpqSneNOe0MgUppHGFPAVAbl0Yrjuo4gQC...', 'ROLE_ADMIN');
INSERT INTO users (id, username, password, role) VALUES (101, 'bia@gmail.com', '$2a$12$hU1TMk4F7qQtuogYvpqSneNOe0MgUppHGFPAVAbl0Yrjuo4gQC...', 'ROLE_USER');
INSERT INTO users (id, username, password, role) VALUES (102, 'bob@gmail.com', '$2a$12$hU1TMk4F7qQtuogYvpqSneNOe0MgUppHGFPAVAbl0Yrjuo4gQC...', 'ROLE_ADMIN');
INSERT INTO users (id, username, password, role) VALUES (103, 'toby@gmail.com', '$2a$12$hU1TMk4F7qQtuogYvpqSneNOe0MgUppHGFPAVAbl0Yrjuo4gQC...', 'ROLE_USER');

INSERT INTO clients (id, name, cpf , id_user) VALUES (100, 'Bianca Silva', '78272954010', 101);
INSERT INTO clients (id, name, cpf , id_user) VALUES (101, 'Roberto Gomes', '36619296076', 100);
INSERT INTO clients (id, name, cpf , id_user) VALUES (102, 'Amanda Gomes', '44747485066', 103);


insert into spots (id, code, status) values (100, 'A-01', 'OCCUPIED');
insert into spots (id, code, status) values (200, 'A-02', 'OCCUPIED');
insert into spots (id, code, status) values (300, 'A-03', 'OCCUPIED');
insert into spots (id, code, status) values (400, 'A-04', 'FREE');
insert into spots (id, code, status) values (500, 'A-05', 'FREE');

insert into client_spot (ticket_number, plate, brand, model, color, check_in_date, id_client, id_parking_spot)
values ('20230313-101300', 'FIT-1020', 'FIAT', 'PALIO', 'VERDE', '2023-03-13 10:15:00', 100, 100);
insert into client_spot (ticket_number, plate, brand, model, color, check_in_date, id_client, id_parking_spot)
values ('20230314-101400', 'SIE-1020', 'FIAT', 'SIENA', 'BRANCO', '2023-03-14 10:15:00', 101, 200);
insert into client_spot (ticket_number, plate, brand, model, color, check_in_date, id_client, id_parking_spot)
values ('20230315-101500', 'FIT-1020', 'FIAT', 'PALIO', 'VERDE', '2023-03-14 10:15:00', 102, 300);