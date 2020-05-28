create table restaurant
(
    id int auto_increment,
    food_category_id int not null,
    area_id int not null,
    name varchar(100) not null,
    description varchar(500) null,
    image longblob null,
    address varchar(200) not null,
    constraint restaurant_pk
        primary key (id),
    FOREIGN KEY (food_category_id) REFERENCES food_category(id),
    FOREIGN KEY (area_id) REFERENCES area(id)
);