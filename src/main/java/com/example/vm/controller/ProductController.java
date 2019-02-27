package com.example.vm.controller;

import com.example.vm.model.entity.Product;
import com.example.vm.service.ProductService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("products")
public class ProductController {
    @Resource
    private ProductService productService;

    @GetMapping("")
    private List<Product> getAll(){
        return productService.getAll();
    }
}
