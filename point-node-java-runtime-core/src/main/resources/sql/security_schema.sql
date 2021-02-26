create table if not exists users(
id integer primary key autoincrement ,
username varchar(50) not null unique ,
password varchar(500) not null ,
enabled boolean not null default true );

create table if not exists authorities(
id integer primary key autoincrement ,
username varchar(50) not null,
authority varchar(50) not null );

create table if not exists groups(
id integer primary key autoincrement ,
group_name varchar(50) not null
);

create table if not exists group_members(
group_id integer not null ,
username varchar(50) not null
);

create table if not exists group_authorities(
group_id integer not null ,
authority varchar(50) not null
);