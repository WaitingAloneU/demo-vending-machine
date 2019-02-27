package com.example.vm.service;

import com.example.vm.model.entity.Coin;
import com.example.vm.model.entity.Record;

import java.util.List;

/**
 * 硬币服务
 */
public interface CoinService {
    /**
     *  获取需要找零的硬币组合.
     */
    List<Coin> giveCoins(Record record);

    /**
     * 投入硬币
     * @param coin
     */
    void saveCoin(Coin coin);
}
