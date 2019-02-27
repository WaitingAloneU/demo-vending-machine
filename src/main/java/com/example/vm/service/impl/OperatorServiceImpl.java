package com.example.vm.service.impl;

import com.example.vm.model.entity.Product;
import com.example.vm.service.OperatorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *  运营人员操作服务
 */
@Slf4j
@Service
public class OperatorServiceImpl implements OperatorService {

    @Override
    public void noticeSupply(List<Product> products) {
        log.info("通知某人进行补充货物, 需要补充的商品信息:{}", products);
    }
}
