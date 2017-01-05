
drop trigger if exists customer_insert;

alter table address drop 
foreign key fk_address_customer;

alter table customer drop 
foreign key fk_customer_address;

alter table orderbox drop 
foreign key fk_order_customer;

alter table orderbox drop 
foreign key fk_order_post;

alter table order_item drop
foreign key fk_order_item_order;

alter table order_item drop
foreign key fk_order_item_item;

alter table user_customer drop
foreign key fk_user_customer_customer;

alter table user_customer drop
foreign key fk_user_customer_user;

drop table if exists order_item;
drop table if exists orderbox;
drop table if exists item;

drop table if exists customer;
drop table if exists post_agent;
drop table if exists address;

drop table if exists user;
drop table if exists user_item;
drop table if exists user_order;
drop table if exists user_customer;
drop table if exists feedback;

