insert into Customer(id, first_name, last_name) values (1001, 'Rahul', 'Dravid');
insert into Customer(id, first_name, last_name) values (1002, 'Steven', 'Smith');
insert into Phone_Number(id, customer_id, number, status) values (1001, 1001, '0411000111', 'inactive');
insert into Phone_Number(id, customer_id, number, status) values (1002, 1001, '0411000222', 'inactive');
insert into Phone_Number(id, customer_id, number, status) values (1003, 1002, '0411111111', 'inactive');
insert into Phone_Number(id, customer_id, number, status) values (1001, 1001, '0411000113', 'inactive');
insert into Phone_Number(id, customer_id, number, status) values (1002, 1001, '0411000223', 'inactive');
insert into Phone_Number(id, customer_id, number, status) values (1003, 1002, '0411111115', 'inactive');