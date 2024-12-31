drop database if exists shop;

create database if not exists shop
    character set utf8mb4
    collate utf8mb4_unicode_ci;

use shop;

select * from category;
select * from category where id = 1;
set @con = 'r';
select @con;
select * from category where parents_path regexp concat('-?',2, '-?');
select * from category where parents_path regexp '-?-?';

SET @regex_pattern = CONCAT('-?',5 ,'-?');
SET @query = CONCAT('SELECT * FROM category WHERE parents_path REGEXP "', @regex_pattern, '"');
PREPARE stmt FROM @query;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;
