create table user
(
    id int auto_increment,
    role_id int not null,
    name varchar(100) not null,
    account varchar(40) not null,
    password varchar(40) not null,
    email varchar(100) null,
    department varchar(100) not null,
    constraint user_pk
        primary key (id),
    FOREIGN KEY (role_id) REFERENCES role(id)
);