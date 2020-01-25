insert into usr (id, username, password, active)
    values (922337203685477580, 'admin', '123', true);

insert into user_role (user_id, roles)
    values (922337203685477580, 'USER'), (922337203685477580, 'ADMIN');