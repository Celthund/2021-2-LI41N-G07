drop table if exists activities;
drop table if exists users cascade;
drop table if exists routes cascade;
drop table if exists sports cascade;

create table if not exists routes (
  rid serial primary key,
  startlocation varchar(80),
  endlocation varchar(80),
  distance int
);

create table if not exists users (
  uid serial primary key,
  name varchar(80) not null,
  email varchar(80) unique not null
);

create table if not exists sports (
   sid serial primary key,
   name varchar(80) not null,
   description varchar(120) not null
);

create table if not exists activities (
    aid serial primary key,
    uid int not null references users (uid),
    rid int references routes (rid),
    sid int references sports (sid),
    date date not null,
    duration bigint not null,
    ts_deleted timestamp
);
