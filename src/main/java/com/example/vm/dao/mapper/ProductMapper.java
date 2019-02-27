package com.example.vm.dao.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.vm.model.entity.Product;
import org.apache.ibatis.annotations.Param;

public interface ProductMapper extends BaseMapper<Product> {
    public Product selectForUpdateById(@Param("productId") Long productId);
}
