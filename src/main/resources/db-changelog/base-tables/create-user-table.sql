create table t_users
(
    id        bigserial    not null,
    email     varchar(255) not null,
    username varchar(255) not null,
    password varchar(255) not null,
    primary key (id)
);

create table t_user_roles (
    user_id bigint not null,
    role_id bigint not null
);

alter table if exists t_user_roles add constraint fk_user_roles foreign key (role_id) references t_roles;
alter table if exists t_user_roles add constraint fk_user_id foreign key (user_id) references t_users;
