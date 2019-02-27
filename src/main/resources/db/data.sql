-- 初始化商品数据
insert into product(id, name, worth, count) values (2001, '奶糖', 10, 0);
insert into product(id, name, worth, count) values (2002, '巧克力', 35, 10);
insert into product(id, name, worth, count) values (2003, '腰果', 55, 0);
insert into product(id, name, worth, count) values (2004, '纯净水', 80, 0);
insert into product(id, name, worth, count) values (2005, '可乐', 140, 0);

-- 初始化硬币数据
insert into coin(id, amount, count) values (1, 10, 10);
insert into coin(id, amount, count) values (2, 25, 10);
insert into coin(id, amount, count) values (3, 100, 10);

-- 记录
insert into record(id, product_id, record_status, amount, insert_amount, insert_comprise, return_amount, return_comprise)
  values (1, 2001, 1, 10, 10, '10', 0, '0')