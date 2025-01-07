create schema if not exists shopping;

create table shopping.compra (
id bigserial primary key,
user_identifier varchar(100) not null,
date date not null,
total float not null
);
create table shopping.item (
compra_id bigserial REFERENCES shopping.compra(id),
product_identifier varchar(100) not null,
price float not null
);