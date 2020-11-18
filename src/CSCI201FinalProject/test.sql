use CSCI201FinalProject;
set @cnt =0;
set @cnt2 =0;
DELIMITER $$


create procedure test_()
begin
while @cnt<10
do
insert into authenticator values (CONCAT('username',@cnt), CONCAT('password',@cnt));
set @cnt2=0;
while @cnt2<4
	do
		call makePost(CONCAT("Test: user: username",@cnt,", Post number: ",@cnt2), CONCAT("username",@cnt), CONCAT("www.plsGiveMoneyToUsername",@cnt,".com"), "MONEY");
        SET @cnt2=@cnt2+1;
	end while;
    SET @cnt= @cnt+1;
end while;

end$$
DELIMITER ;

call test_();
SELECT * from Posts;
Select * from Authenticator;
select @cnt;