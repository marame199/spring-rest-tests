insert  into account(id,balance,creation_date,is_active,number,type) values ('1',15,CURRENT_TIMESTAMP,1,'0000001','saving1');
insert  into account(id,balance,creation_date,is_active,number,type) values ('2',16,CURRENT_TIMESTAMP,1,'0000002','saving2');
insert  into account(id,balance,creation_date,is_active,number,type) values ('20',15,CURRENT_TIMESTAMP,1,'0000001','saving20');


insert  into transaction(id,account_Id,number,balance) values ('1','1','0000012',12);
insert  into transaction(id,account_Id,number,balance) values ('2','1','0000013',13);
insert  into transaction(id,account_Id,number,balance) values ('3','2','0000014',14);
insert  into transaction(id,account_Id,number,balance) values ('4','2','0000015',15);