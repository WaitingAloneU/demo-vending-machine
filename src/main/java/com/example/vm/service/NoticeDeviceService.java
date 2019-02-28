package com.example.vm.service;

import com.example.vm.model.entity.Coin;
import com.example.vm.model.entity.Record;

import java.util.List;

/**
 * 通知设备接口, 该接口与硬件设备配合, 初始化信号发送给硬件设备
 * 未实现, 用log.info模拟.
 */
public interface NoticeDeviceService {

    /**
     * 通知设备, 用户未投币
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

    /**
     * 通知设备,硬币不够找零
     */
    void noticeDeviceCoinNotEnough();

    /**
     * 通知设备吐出指定位置的商品
     * @param record
     */
    void noticeDeviceGiveProduct(Record record);

    /**
     * 通知设备吐出指定组合的硬币
     * @param coins
     */
    void noticeDeviceGiveCoins(List<Coin> coins);
}
