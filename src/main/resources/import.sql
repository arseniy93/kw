
INSERT INTO users ( firstname, lastname, middle_name, password, role, username) VALUES ( 'Инна', 'Смольянинова', 'Порфнрьевна', 'qwerty', 'ROLE_USER', 'inna9544');
INSERT INTO client_type(id,name) VALUES (1,'новый');
INSERT INTO client_type(id,name) VALUES (2,'давний');
INSERT INTO client_type(id,name) VALUES (3,'постоянный');

INSERT INTO employee_type(id,name) VALUES (1,'новый');
INSERT INTO employee_type(id,name) VALUES (2,'давний');

INSERT INTO client(lastname,firstname,middle_name,email,client_type_id) VALUES ('Галимов','Ринат','Зуфарович','r.galimow@mail.ru',1);
INSERT INTO client(lastname,firstname,middle_name,email,client_type_id) VALUES ('Круглов', 'Фёдор' ,'Иванович','rinatgalimov1992@gmail.com',2);
INSERT INTO client(lastname,firstname,middle_name,email,client_type_id) VALUES ('Гришин', 'Алексей', 'Макарович','Berufskiller@yandex.ru',3);
INSERT INTO employee(lastname,firstname,middle_name,email,employee_type_id) VALUES ('Гришкин','Тигран','Тахирович','rinat_galimov_1992@mail.ru',1);
INSERT INTO employee(lastname,firstname,middle_name,email,employee_type_id) VALUES ('Томаев','Ислам','Маратович','rinblch2002@mail.ru',2);
