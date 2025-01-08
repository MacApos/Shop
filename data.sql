drop database if exists shop;

create database if not exists shop
    character set utf8mb4
    collate utf8mb4_unicode_ci;

use shop;

select * from category;
select * from category where id = 1;
set @con = 'r';
select @con;
select * from category where hierarchy_path regexp '(?:^|-)1(?:-|$)';
select * from category where hierarchy_path regexp '-?1-?';

SET @regex_pattern = CONCAT('-?',5 ,'-?');
SET @query = CONCAT('SELECT * FROM category WHERE parents_path REGEXP "', @regex_pattern, '"');
PREPARE stmt FROM @query;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

insert into user(username, password, enabled, email)
values ('b', '$2a$12$87.hiWT69Dw/26pgLf96TerHU/McZRyZi/3libAOCJ4DRUsvQ0BSG', 1, 'b'),
       ('c', '$2a$12$L2P/54EPLdH8gXVgVTfOYuEGnvArlLHB76eaARbExTcfYBG1mg8pe', 1, 'c');

set @admin = (select id
              from user
              where username like 'b');
set @user = (select id
             from user
             where username like 'c');
select @admin;
select @user;
insert into role(user_id, name)
values (@admin, 'ROLE_ADMIN'),
       (@admin, 'ROLE_USER'),
       (@user, 'ROLE_USER');

select username, password, enabled from user where email = 'a';
select r.* from role r left join user u on u.id = r.user_id where email = 'a';
