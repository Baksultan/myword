create table t_texts
(
    id      bigserial    not null,
    user_id bigint,
    s3Key text,
    title   varchar(255) not null,
    url     varchar(255) not null,
    views bigint,
    primary key (id)
);

alter table if exists t_texts
    add constraint user_id_fk foreign key (user_id) references t_users;