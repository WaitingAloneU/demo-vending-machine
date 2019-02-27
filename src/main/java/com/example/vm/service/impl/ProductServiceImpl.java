package com.example.vm.service.impl;

import com.example.vm.dao.mapper.ProductMapper;
import com.example.vm.model.entity.Product;
import com.example.vm.model.entity.Record;
import com.example.vm.service.NoticeDeviceService;
import com.example.vm.service.OperatorService;
import com.example.vm.service.ProductService;
import com.example.vm.service.RecordService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Resource
    private OperatorService operatorService;

    @Resource
    private RecordService recordService;

    @Resource
    private NoticeDeviceService noticeDeviceService;

    @Resource
    private ProductMapper productMapper;

    @Override
    public List<Product> getAll() {
        return productMapper.selectList(null);
    }

    @Override
    public Boolean chooseProduct(Product product){
        Product result = productMapper.selectById(product.getId());
        // 判断库存
        if(result.getCount() < INVENTORY_THREDHODE){
            List<Product> products = new ArrayList<>();
            products.add(result);
            operatorService.noticeSupply(products);
        }

        // 记录record数据
        recordService.updateRecordInfo(result);

        // 库存不够,无法购买
        if(result.getCount() < 1){
            noticeDeviceService.noticeDeviceInventoryDeficiency();
            return false;
        } else {
            // 库存足够可以购买
            noticeDeviceService.noticeDeviceInventoryEnough();
            return true;
        }
    }

    @Override
    public Boolean confirmProductCount(Record record) {
        Product product = productMapper.selectById(record.getProudctId());
        return product.getCount() > 1;
    }

    @Override
    @Transactional
    public void subProductCount(Record record) {
        Product product = productMapper.selectById(record.getProudctId());
        product.setCount(product.getCount() - 1);
        productMapper.updateById(product);
    }
}
