
 drop database DS2Project
 go

 create database DS2Project
 go

 use DS2Project
 go

create table InternalLink (
	id uniqueidentifier primary key default newid(),
	create_timestamp datetime not null default getdate(), 
	modify_timestamp datetime not null default getdate(), 
	description varchar(500) not null unique
)

create table SocialMedia (
	id uniqueidentifier primary key default newid(),
	create_timestamp datetime not null default getdate(), 
	modify_timestamp datetime not null default getdate(), 
	description varchar(500) not null unique
)

create table Color (
	id uniqueidentifier primary key default newid(),
	create_timestamp datetime not null default getdate(), 
	modify_timestamp datetime not null default getdate(), 
	description varchar(50) not null unique
)

create table PhoneNumber (
	id uniqueidentifier primary key default newid(),
	create_timestamp datetime not null default getdate(), 
	modify_timestamp datetime not null default getdate(), 
	description varchar(500) not null unique
)

create table ExternalLink (
	id uniqueidentifier primary key default newid(),
	create_timestamp datetime default getdate(), 
	modify_timestamp datetime default getdate(), 
	description varchar(500) unique
)

create table Email (
	id uniqueidentifier primary key default newid(),
	create_timestamp datetime default getdate(), 
	modify_timestamp datetime default getdate(), 
	description varchar(100) unique
)

create table CrawlerQueue (
	id uniqueidentifier primary key default newid(),
	create_timestamp datetime not null default getdate(), 
	modify_timestamp datetime not null default getdate(), 
	description varchar(500) not null unique
)

create table ScraperQueue (
	id uniqueidentifier primary key default newid(),
	create_timestamp datetime not null default getdate(), 
	modify_timestamp datetime not null default getdate(), 
	description varchar(500) not null unique
)
