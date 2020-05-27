create table comment
(
	id int auto_increment,
	user_id int not null,
	restaurant_id int not null,
	rate int not null,
	description varchar(500) null,
	image longblob null,
	date timestamp not null,
	constraint comment_pk
		primary key (id, user_id, restaurant_id),
    	FOREIGN KEY (user_id) REFERENCES user(id),
    	FOREIGN KEY (restaurant_id) REFERENCES restaurant(id),
    	constraint chk_rate
        	check (rate >= 1 and rate <= 5)
);