-- // add UserDetails table
-- Migration SQL that makes the change goes here.
create table user_details (
  user_id bigint not null auto_increment primary key,
  first_name varchar(32),
  last_name varchar(32),
  about varchar(1024),
  is_online boolean not null default false,
  skype varchar(32),
  linkedin varchar(255),
  rating float not null default '0.00',
  rating_votes_count int NOT NULL default 0,

  foreign key (user_id) references "users"(id)
);

insert into `user_details`("user_id", "first_name", "last_name") values
  (1, 'Alexander', 'Kozlov'),
  (2, null, null);


-- //@UNDO
-- SQL to undo the change goes here.
drop table user_details;