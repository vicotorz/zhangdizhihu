DROP TABLE IF EXISTS question
create table question(
id int not null auto_increment,
title varchar(255) not null,
content text not null,
user_id int not null,
created_date datetime not null,
comment_count int not null,
primary key(id),
UNIQUE  INDEX  date_index ( created_date  ASC) 
);

DROP TABLE IF EXISTS user;
CREATE TABLE user(
id int(11) unsigned NOT NULL AUTO_INCREMENT,
name varchar(64) NOT NULL DEFAULT '',
password varchar(128) NOT NULL DEFAULT '',
salt varchar(32) NOT NULL DEFAULT '',
head_url varchar(256) NOT NULL DEFAULT '',
PRIMARY KEY(id),
UNIQUE KEY name(name)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;