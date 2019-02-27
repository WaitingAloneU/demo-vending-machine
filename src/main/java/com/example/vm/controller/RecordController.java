package com.example.vm.controller;

import com.example.vm.model.entity.Product;
import com.example.vm.model.entity.Record;
import com.example.vm.service.ProductService;
import com.example.vm.service.RecordService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("records")
public class RecordController {
    @Resource
    private RecordService recordService;

    @GetMapping("")
    private List<Record> getAll(){
        return recordService.getAll();
    }
}
