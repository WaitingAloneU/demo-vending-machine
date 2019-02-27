package com.example.vm.service;

import com.example.vm.model.entity.Coin;
import com.example.vm.model.entity.Record;

import java.util.List;

public interface NoticeDeviceService {

    /**
     * 用户未投币,通知设备
     */
    void noticeDeviceInsert();

    /**
     * 库存不足, 通知设备
     */
    void noticeDeviceInventoryDeficiency();

    /**
     * 库存足够, 通知设备
     */
    void noticeDeviceInventoryEnough();

    void noticeDeviceCoinNotEnough();

    void noticeDeviceGiveProduct(Record record);

    void noticeDeviceGiveCoins(List<Coin> coins);
}
