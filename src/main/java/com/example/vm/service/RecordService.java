package com.example.vm.service;

import com.example.vm.model.entity.Coin;
import com.example.vm.model.entity.Product;
import com.example.vm.model.entity.Record;

import java.util.List;

/**
 * 交易记录服务接口
 */
public interface RecordService {

    /**
     * 查询所有的交易记录
     * @return
     */
    List<Record> getAll();

    /**
     * 是否已经有未结束的交易
     * @return
     */
    boolean isInsertCoin();

    /**
     * 查询最后一条记录.
     * @return
     */
    Record getLastRecord();

    /**
     * 更新记录信息中的商品信息
     * @param product
     */
    void updateRecordInfo(Product product);

    /**
     *  确认,记录中的金额是否足够购买商品
     * @return
     */
    Record confirmAmountAndInsert();

    /**
     * 确认交易后, 补充记录信息
     * @param record
     * @param coins
     */
    void completeRecord(Record record, List<Coin> coins);

    /**
     * 更新记录信息中的硬币,金额,信息
     * @param coin
     */
    void updateRecordInfo(Coin coin);
}
