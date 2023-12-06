insert into t_users (id, email, username, password)
values (1, 'admin@gmail.com', 'admin', '$2a$12$VflCGqd5rsBNSZ6jHZLyY.1/mFibqDIEeGcKgUJNjUF4Hu94R9AyS');

insert into t_users (id, email, username, password)
values (2, 'user@gmail.com', 'user', '$2a$12$VflCGqd5rsBNSZ6jHZLyY.1/mFibqDIEeGcKgUJNjUF4Hu94R9AyS');

insert into t_user_roles (user_id, role_id) values (1, 1);
insert into t_user_roles (user_id, role_id) values (2, 2);