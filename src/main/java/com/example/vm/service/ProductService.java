package com.example.vm.service;

import com.example.vm.model.entity.Product;
import com.example.vm.model.entity.Record;

import java.util.List;

public interface ProductService{

    public static Integer INVENTORY_THREDHODE = 3;

    public List<Product> getAll();

    /**
     * 选择商品
     * @param product
     * @return
     */
    public Boolean chooseProduct(Product product);

    Boolean confirmProductCount(Record record);

    void subProductCount(Record record);
}