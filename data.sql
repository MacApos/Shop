drop database if exists shop;
create database if not exists shop
    character set utf8mb4
    collate utf8mb4_0900_as_cs;
use shop;

select *
from category;
select *
from category
where id = 1;
set @con = 'r';
select @con;
select *
from category
where hierarchy_path regexp '(?:^|-)1(?:-|$)';
select *
from category
where hierarchy_path regexp '-?1-?';

SET @regex_pattern = CONCAT('-?', 5, '-?');
SET @query = CONCAT('SELECT * FROM category WHERE parents_path REGEXP "', @regex_pattern, '"');
PREPARE stmt FROM @query;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

insert into user(enabled, email, firstname, lastname, password, username)
values (true, 'adamnian@gmial.com', 'Adamnian', 'Nowakczyk',
        '$2a$10$ylTLTLPnxnl85HL9iuJA6eLLKWJ7chN/Hrlmp2gNLnB5hDy.WzIRe', 'adamnian'),
       (true, 'usek@gmial.com', 'Usek', 'Juserczyk',
        '$2a$10$ylTLTLPnxnl85HL9iuJA6eLLKWJ7chN/Hrlmp2gNLnB5hDy.WzIRe', 'usek');

set @adminEmail = (select email
                   from user
                   where username like 'admin');
set @userEmail = (select email
                  from user
                  where username like 'user');

insert into role(email, name)
values (@adminEmail, 'ROLE_ADMIN'),
       (@adminEmail, 'ROLE_USER'),
       (@userEmail, 'ROLE_USER');

set @adminId = (select id
                from user
                where email like @adminEmail);
set @userId = (select id
               from user
               where email like @userEmail);

insert into cart (user_id) value (@userId);
set @userCart = (select id
                 from cart
                 where user_id like @userId);

set @product1 = (select id from product where name like 'Czapka 1');
set @product2 = (select id from product where name like 'Dywan wzor 1');
set @product3 = (select id from product where name like 'Torba 1');

truncate  cart_item;
insert into cart_item (quantity, cart_id, product_id)
values (2, @userCart, @product1),
       (1, @userCart, @product2),
       (1, @userCart, @product3);


