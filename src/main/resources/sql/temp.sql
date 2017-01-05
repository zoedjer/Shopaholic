DELIMITER $$
create procedure newOrder(
	user_id bigint,
	customer_id bigint,
	post_id bigint,
	tracking_number varchar(100),
	items varchar(255)
)
BEGIN
	SET @userId=user_id;
	
	SET @customerId=customer_id;
	SET @postId=post_id;
	SET @trackNum=tracking_number;
	
	insert into orderbox(customer_id,post_id,tracking_number,status,create_date) 
	values (@customerId,@postId,@trackNum,'active',now());
	
	SET @curOrderId = LAST_INSERT_ID();
	
	insert into 
END $$ 
DELIMITER ;