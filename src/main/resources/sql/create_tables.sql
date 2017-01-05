create table feedback(
	id bigint unsigned not null auto_increment primary key,
	user_id bigint unsigned,
	message text,
	create_date timestamp null default null
) ENGINE=InnoDB;

create table user (
	id bigint unsigned not null auto_increment primary key,
	username varchar(100),
	email varchar(100) not null,
	password varchar(255) not null,
	user_role varchar(50) not null,
	create_date timestamp null default null
) ENGINE=InnoDB;

create table user_order(
	id bigint unsigned not null auto_increment primary key,
	user_id bigint unsigned,
	order_id bigint unsigned
) ENGINE=InnoDB;

create table user_customer(
	id bigint unsigned not null auto_increment primary key,
	user_id bigint unsigned,
	customer_id bigint unsigned
) ENGINE=InnoDB;

create table user_item (
	id bigint unsigned not null auto_increment primary key,
	user_id bigint unsigned,
	item_id bigint unsigned
) ENGINE=InnoDB;

create table post_agent(
	id bigint unsigned not null auto_increment primary key,
	name varchar(255),
	website varchar(255),
	create_date timestamp null default null	
) ENGINE=InnoDB;

create table item (
	id bigint unsigned not null auto_increment primary key,
	name varchar(255),
	category varchar(255),
	price decimal(10,2),
	brand varchar(255),
	create_date timestamp null default null,
	modify_date timestamp null default null
) ENGINE=InnoDB;

create table address(
	id bigint unsigned not null auto_increment primary key,
	customer_id bigint unsigned,
	line_one varchar(255),
	line_two varchar(255),
	city varchar(255),
	county varchar(255),
	country varchar(255),
	create_date timestamp null default null
) ENGINE=InnoDB;

create table customer(
	id bigint unsigned not null auto_increment primary key,
	first_name varchar(255),
	last_name varchar(255),
	phone varchar(255),
	email varchar(255),
	status varchar(50),
	address_id bigint unsigned,
	line_one varchar(255),
	line_two varchar(255),
	city varchar(255),
	county varchar(255),
	country varchar(255),
	create_date timestamp null default null,
	modify_date timestamp null default null
	
) ENGINE=InnoDB;

create table orderbox(
	id bigint unsigned not null auto_increment primary key,
	customer_id bigint unsigned,
	post_id bigint unsigned,
	tracking_number varchar(255),
	status varchar(255),
	shipping_fee decimal(10,2),
	create_date timestamp null default null,
	modify_date timestamp null default null
) ENGINE=InnoDB;

create table order_item(
	id bigint unsigned not null auto_increment primary key,
	order_id bigint unsigned,
	item_id bigint unsigned,
	price_sold decimal(10,2),
	quantity int
) ENGINE=InnoDB;

alter table address add constraint fk_address_customer
foreign key (customer_id) references customer(id);

alter table customer add constraint fk_customer_address
foreign key (address_id) references address(id);

alter table orderbox add constraint fk_order_customer
foreign key (customer_id) references customer(id);

alter table orderbox add constraint fk_order_post
foreign key (post_id) references post_agent(id);

alter table order_item add constraint fk_order_item_order
foreign key (order_id) references orderbox(id);

alter table order_item add constraint fk_order_item_item
foreign key (item_id) references item(id);

alter table user_customer add constraint fk_user_customer_customer
foreign key (customer_id) references customer(id);

alter table user_customer add constraint fk_user_customer_user
foreign key (user_id) references user(id);

DELIMITER $$
create trigger customer_insert after insert on customer
for each row
begin
	insert into address (customer_id,line_one,line_two,city,county,country,create_date) 
	values (new.id,new.line_one,new.line_two,new.city,new.county,new.country,now());
	
end$$
DELIMITER ;


