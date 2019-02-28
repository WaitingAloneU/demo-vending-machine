package com.example.vm.service.impl;

import com.example.vm.model.entity.Coin;
import com.example.vm.model.entity.Record;
import com.example.vm.service.NoticeDeviceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;


@Slf4j
@Service
public class NoticeDeviceServiceImpl implements NoticeDeviceService {

    @Override
    public void noticeDeviceInsert() {
        log.info("通知设备,提示用户,请投币");
    }

    @Override
    public void noticeDeviceInventoryDeficiency() {
        log.info("通知设备,提示用户,库存不足,无法购买");
    }

    @Override
    public void noticeDeviceInventoryEnough() {
        log.info("通知设备,库存足够,可以购买");
    }

    @Override
    public void noticeDeviceCoinNotEnough() {
        log.info("通知设备,提示用户, 设备内硬币不够找零");
    }

    @Override
    public void noticeDeviceGiveProduct(Record record) {
        log.info("通知设备,吐出商品, 位置号:{}", record.getProudctId());
    }

    @Override
    public void noticeDeviceGiveCoins(List<Coin> coins) {
        if(coins == null || coins.size() == 0) {
            log.info("无需找零");
        } else {
            // TODO, 硬币转成对应的信号值.
            log.info("通知设备,找零, 金币组成:{}", coins);
        }
    }
}
