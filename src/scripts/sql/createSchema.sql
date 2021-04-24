drop table if exists users;
drop table if exists routes;
drop table if exists sports;
drop table if exists activities;

create table routes (
  rid serial primary key,
  startlocation varchar(80),
  endlocation varchar(80),
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
    sid int,
    date date not null,
    duration bigint not null
);


------------ SQL -----------
drop table if exists users;
drop table if exists routes;
drop table if exists sports;
drop table if exists activities;

create table routes (
  [rid] int primary key IDENTITY,
  [startlocation] varchar(80),
  [endlocation] varchar(80),
  [distance] int
);

create table users (
  [uid] int primary key IDENTITY,
  [name] varchar(80) not null,
  [email] varchar(80) unique not null
);

create table sports (
   [sid] int primary key IDENTITY,
   [name] varchar(80) not null,
   [description] varchar(120) not null
);

create table activities (
    [aid] int primary key IDENTITY,
    [uid] int FOREIGN KEY REFERENCES users([uid]),
    [rid] int NULL FOREIGN KEY REFERENCES routes([rid]),
    [sid] int FOREIGN KEY REFERENCES sports([sid]),
    [date] date not null,
    [duration] bigint not null
);


