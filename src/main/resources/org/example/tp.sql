Drop schema if exists TP; 
create database if not exists TP; 
use TP; 
create table if not exists Client 
( ID integer auto_increment unique not null, 
 name varchar(30), 
 address varchar(40),
 primary key(ID) 
); 

create table if not exists Product 
( ID integer primary key auto_increment  unique not null, 
  name varchar(30), 
  price int,
  stock integer
); 

create table if not exists Orders 
( ID integer primary key auto_increment not null,
  clientID integer not null, 
  productID integer not null,
  purchaseDate date,
  amount integer,
  total integer
);

delimiter //
create trigger t3 before INSERT  ON Orders
for each row
begin
 if(new.amount > (select stock from Product where ID = new.productID)) then
 SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Stoc depasit';
   end if;
 
end;//



 delimiter //
create trigger t1 after INSERT  ON Orders
for each row
begin
update Product p SET stock = stock - new.amount where p.ID = new.productID; 
end;//

 delimiter //
create trigger t2 before INSERT ON Orders
for each row
begin
  SET new.total = (select price from Product where ID = new.productID)*new.amount; 
end;//

create trigger t4 before update ON Orders
for each row
begin
  SET new.total = (select price from Product where ID = new.productID)*new.amount; 
end;//

insert into Product values(1,"Laptop", 500, 10);
insert into Product values(2,"Telefon", 1000, 10);
insert into Client values(1, "Catalin", "Cluj-Napoca");
insert into Client values(2, "Cosmin", "Satu Mare");

select *
from Product;
insert into Orders(clientID, productID, purchaseDate, amount) values
(1, 1, "2022-02-11", 2),
(1, 1, "2022-02-11", 2);

select *
from Product;

select *
from Orders;


select *
from Client;


