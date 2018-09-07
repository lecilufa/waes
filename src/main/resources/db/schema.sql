drop table if exists TextStore;  
create table TextStore (
	ID BIGINT PRIMARY KEY AUTO_INCREMENT, 
	LEFTTEXT VARCHAR(255), 
	RIGHTTEXT VARCHAR(255)
);