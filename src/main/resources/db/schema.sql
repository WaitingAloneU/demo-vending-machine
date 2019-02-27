drop table if exists product;
drop table if exists record;
drop table if exists coin;

-- 商品实体：主键，商品名，价格，库存
create table product (id int primary key auto_increment, name varchar, worth int, count int);
-- 购买记录：主键，商品ID，购买状态(投币,完成,取消)，金额, 投入金额, 投入由哪些金额的硬币组成, 找零金额, 找零金额由哪些金额的硬币组成
create table record (id int primary key auto_increment, product_id int, record_status int, amount int, insert_amount int, insert_comprise varchar, return_amount int, return_comprise varchar);
-- 硬币：主键，金额，数量
create table coin (id int primary key auto_increment, amount int, count int);