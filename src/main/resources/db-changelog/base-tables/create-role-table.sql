create table t_roles (
    id serial not null,
    name varchar(255) not null,
    primary key (id)
);

insert into t_roles (id, name) values (1, 'ROLE_ADMIN');
insert into t_roles (id, name) values (2, 'ROLE_USER');