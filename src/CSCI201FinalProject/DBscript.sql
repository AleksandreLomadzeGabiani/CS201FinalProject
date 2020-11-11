DROP DATABASE IF EXISTS CSCI201FinalProject;
CREATE DATABASE CSCI201FinalProject;
use CSCI201FinalProject;

create table Authenticator (
  username char(30) not null unique primary key, 
  password varchar(30) not null
  );
DROP PROCEDURE if exists registerUser;
DROP FUNCTION if exists authenticate;
DROP FUNCTION if exists usernameExists;
DELIMITER $$
CREATE FUNCTION authenticate(user varchar(30), pass varchar(30))
RETURNS BIT
begin
    RETURN
	CASE
		WHEN EXISTS 
        (SELECT * FROM Authenticator WHERE username = user AND password = pass ) 
        THEN 1
        ELSE 0
	END;
end$$

CREATE FUNCTION usernameExists(user varchar(30))
RETURNS BIT
begin
    RETURN
	CASE
		WHEN EXISTS 
        (SELECT * FROM Authenticator WHERE username = user) 
        THEN 1
        ELSE 0
	END;
end$$

CREATE PROCEDURE registerUser(IN user varchar(30), IN pass varchar(30))
begin
	INSERT INTO Authenticator(username, password)
	VALUES (user, pass);
end$$

DELIMITER ;

create table Posts(
	Dateof date NOT NULL,
    PostId int NOT NULL AUTO_INCREMENT,
    Post varchar(2000) NOT NULL,
    Title varchar(2000) NOT NULL,
    Paymentlink varchar(2000) NOT NULL,
    username char(30) not null unique,
    PRIMARY KEY (PostId),
    FOREIGN KEY (username) references authenticator(username)
);

set @last_id=0;

DELIMITER $$
CREATE PROCEDURE makePost(IN userpost varchar(2000), IN usern char(30), IN paylink varchar(2000), IN title varchar(2000))
begin
	INSERT INTO Posts (Dateof, Post, username, Paymentlink, Title) values (curdate(), userpost, usern, paylink, title);
    SET @last_id=@last_id+1;
end$$
DELIMITER ;

set @last_id=@last_id-15;
SELECT * FROM Posts  WHERE PostId>=last_id-15 order by Dateof limit 15;





    