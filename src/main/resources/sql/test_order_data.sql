insert into user (username,email,password,user_role,create_date)
values ('user@user.com','user@user.com','dXNlcg==','role_user',now());

set @user_id = last_insert_id();

insert into customer (first_name,last_name,phone,email,status,line_one,line_two,city,county,country,create_date) 
values ('kai','kai','087-xxxx','user@user.com','active','yinghua','hua','puyang','zhong','china',now());

set @customer_id = last_insert_id();

insert into user_customer (user_id,customer_id) values (@user_id,@customer_id);

insert into item (name,price,brand,category,create_date)
values('bag',89.23,'amani','wearing',now());

set @it_id_1 = last_insert_id();

insert into item (name,price,brand,category,create_date)
values('necklace',100.68,'jd','wearing',now());

set @it_id_2 = last_insert_id();

insert into item (name,price,brand,category,create_date)
values('ring',72.39,'diam','wearing',now());

set @it_id_3 = last_insert_id();

insert into user_item (user_id,item_id) values (@user_id,@it_id_1),(@user_id,@it_id_2),(@user_id,@it_id_3);

insert into post_agent (name,website,create_date) values 
('DHL','DHL@DHL.com',now()),
('Shunfeng','Shunfeng@post.com',now());