create table restaurant
(
	id int auto_increment,
	name varchar(100) not null,
	food_category varchar(100) not null,
	description varchar(500) null,
	image longblob null,
	address varchar(200) not null,
	area varchar(100) not null,
	constraint restaurant_pk
		primary key (id)
);