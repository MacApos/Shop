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

set @adminId = (select id
                from user
                where username like 'admin');
set @userId = (select id
               from user
               where username like 'user');

insert into role(user_id, name)
values (@adminId, 'ROLE_ADMIN'),
       (@adminId, 'ROLE_USER'),
       (@userId, 'ROLE_USER');

insert into cart (user_id) value (@userId);
set @userCart = (select id
                 from cart
                 where user_id like @userId);

set @product1 = (select id
                 from product
                 where name like 'Czapka 1');
set @product2 = (select id
                 from product
                 where name like 'Dywan wzór 1');
set @product3 = (select id
                 from product
                 where name like 'Torba 1');

select @product1;
select @product2;
select @product3;

truncate cart_item;
insert into cart_item (quantity, cart_id, product_id)
values (2, @userCart, @product1),
       (1, @userCart, @product2),
       (1, @userCart, @product3);

select *
from cart
where user_id = (select id from user where email like 'user@gmail.com');

select u.email, r.name
from role r
         left join user u on r.user_id = u.id
where u.email = ?;

select ci.*
from cart_item ci
         left join cart c on ci.cart_id = c.id
         left join user u on c.user_id = u.id
where u.email = 'admin@gmail.com';

select 1
from cart_item ci
         inner join cart c on ci.cart_id = c.id
         inner join user u on c.user_id = u.id
where ci.id = 1
  and u.email = 'user@gmail.com';

select 1
from category
where name like 'Category3'
  and parent_id = 2;


select id, name
from category
where id = 5
union all
select id, name
from category;

select *
from category
         left join product on category.id = product.category_id;
select *
from category
         right join product on category.id = product.category_id;
select *
from category
         inner join product on category.id = product.category_id;

select *
from category c1
         left join category c2 on c1.id = c2.parent_id
where c1.id < 5;
select *
from category c1
         right join category c2 on c1.id = c2.parent_id
where c1.id < 5;
select *
from category c1
         inner join category c2 on c1.id = c2.parent_id
where c1.id < 5;

with recursive CategoryTree as (select id, parent_id
                                from category
                                where id = 1
                                union all
                                select c.id, c.parent_id
                                from category c
                                         inner join CategoryTree ct on ct.id = c.parent_id)
select p.*
from product p
         inner join CategoryTree ct on ct.id = p.category_id;


with recursive CategoryTree as (select id, parent_id
                                from category
                                where id = 1
                                union all
                                select c.id, c.parent_id
                                from category c
                                         inner join CategoryTree ct on ct.id = c.parent_id)
select count(*)
from product p
         inner join CategoryTree ct on ct.id = p.category_id;
