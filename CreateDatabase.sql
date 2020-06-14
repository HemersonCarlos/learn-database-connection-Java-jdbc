-- create database register_person;
use register_person;

---------------------------------------

drop table if exists Person;
create table Person(
	personId int not null,
	personName varchar(255) not null,
	personAge int not null,
	primary key ( personId )
);

----------------------------------------

drop table if exists EmailRegistration;
create table EmailRegistration(
	emailId int not null,
	personEmail varchar(255) not null,
	primary key ( emailId )
);

select * from Person;
select * from EmailRegistration;


SELECT DISTINCT Person.personId, Person.personName, Person.personAge, emailId, personEmail
FROM Person JOIN EmailRegistration
ON EmailRegistration.emailId = Person.personId;