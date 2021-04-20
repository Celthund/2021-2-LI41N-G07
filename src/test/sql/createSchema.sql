drop table if exists users;
drop table if exists routes;
drop table if exists sports;
drop table if exists activities;

create table routes (
  rid serial primary key,
  startLocation varchar(80),
  endLocation varchar(80),
  distance int
);

create table users (
  uid serial primary key,
  name varchar(80) not null,
  email varchar(80) unique not null
);

create table sports (
   sid serial primary key,
   name varchar(80) not null,
   description varchar(120) not null
);

create table activities (
    aid serial primary key,
    uid int not null,
    rid int,
    date date not null,
    duration bigint not null
);