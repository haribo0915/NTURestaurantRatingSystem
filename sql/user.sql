create table user
(
	id int auto_increment,
	name varchar(100) not null,
	account varchar(40) not null,
	password varchar(40) not null,
	email varchar(100) null,
	role varchar(40) not null,
	department varchar(100) not null,
	constraint user_pk
		primary key (id)
);