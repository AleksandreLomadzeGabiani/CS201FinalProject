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
