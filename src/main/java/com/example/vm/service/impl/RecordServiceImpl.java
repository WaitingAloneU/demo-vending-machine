package com.example.vm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.EmptyWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.vm.dao.mapper.RecordMapper;
import com.example.vm.model.entity.Coin;
import com.example.vm.model.entity.Product;
import com.example.vm.model.entity.Record;
import com.example.vm.service.RecordService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
public class RecordServiceImpl implements RecordService {
    @Resource
    private RecordMapper recordMapper;
    @Override
    public List<Record> getAll() {
        return recordMapper.selectList(new EmptyWrapper<>());
    }

    /**
     * 取最后一条,只要状态是0, 就说明投币了.
     * @return
     */
    @Override
    public boolean isInsertCoin() {
        Record record = getLastRecord();
        if(record != null){
            return record.getRecordStatus() == 0;
        }
        return false;
    }

    @Override
    public Record getLastRecord(){
        QueryWrapper<Record> wrapper = new QueryWrapper<>();
        wrapper.orderBy(true, false, "id");
        wrapper.last("limit 1");
        Record record = recordMapper.selectOne(wrapper);
        return record;
    }

    @Override
    public void updateRecordInfo(Product product) {
        // 查询是否已经投币
        Record record = getLastRecord();
        if(record!= null && record.getRecordStatus() == 0){
            // 更新record记录

            Integer sourceStatus = record.getRecordStatus();

            // 用 record_status 乐观锁, 建议用单独的字段做乐观锁,非业务字段
            QueryWrapper<Record> wrapper = new QueryWrapper<>();
            wrapper.eq("record_status", sourceStatus);
            record.setProudctId(product.getId());
            record.setAmount(product.getWorth());
            int t = recordMapper.update(record, wrapper);

            if(t == 0){
                log.info("选择商品第一次更新record结果:{}, record信息:{}", t, record);
                Record secondRecord = getLastRecord();
                sourceStatus = secondRecord.getAmount();
                secondRecord.setProudctId(product.getId());
                secondRecord.setAmount(sourceStatus + product.getWorth());

                QueryWrapper<Record> secondWrapper = new QueryWrapper<>();
                wrapper.eq("record_status", sourceStatus);
                t = recordMapper.update(secondRecord, secondWrapper);
                if(t == 0){
                    log.error("record未更新,amount值一直更新, record信息:{}", secondRecord);
                }
            }
            log.info("record更新结果:{},record信息:{}", t, record);
        } else {
            // record 没有数据, 或者record的status不是0,都 需要插入一条record数据
            Record newRecord = new Record();
            newRecord.setProudctId(product.getId());
            newRecord.setAmount(product.getWorth());
            newRecord.setRecordStatus(0);
            int t = recordMapper.insert(newRecord);
            log.info("record插入结果:{},record信息:{}", t, newRecord);
        }

        List<Record> t = recordMapper.selectList(new EmptyWrapper<>());
        log.info("records长度:{}", t.size());
    }

    @Override
    public Record confirmAmountAndInsert() {
        Record record = getLastRecord();
        log.info("record---- "+ record);
        if(record != null && record.getRecordStatus() == 0 ){
            // 投入的硬币总额 > 选择的商品价格
            if(record.getInsertAmount() >= record.getAmount()) return record;
        }
        return null;
    }

    @Override
    public void completeRecord(Record record, List<Coin> coins) {
        StringBuilder returnComprise = new StringBuilder();
        Integer returnAmount = 0;

        for (Coin coin : coins) {
            returnAmount += coin.getAmount() * coin.getCount();
            Integer[] amounts = new Integer[coin.getCount()];
            for (int i=0; i<coin.getCount(); i++){
                amounts[i] = coin.getAmount();
            }
            returnComprise.append(StringUtils.join(amounts, ","));
        }

        record.setRecordStatus(1);
        record.setReturnAmount(returnAmount);
        record.setReturnComprise(returnComprise.toString());
        int t = recordMapper.update(record, new EmptyWrapper<>());
        if(t == 0){
            log.error("确认最后更新record结果:{}, record信息:{}", t, record);
        }
    }

    @Override
    public void updateRecordInfo(Coin coin) {
        Record record = getLastRecord();
        if(record != null && record.getRecordStatus() == 0){
            if(record.getInsertAmount() == null){
                record.setInsertAmount(coin.getAmount());
            }else {
                record.setInsertAmount(record.getInsertAmount() + coin.getAmount());
            }
            if(record.getInsertComprise() == null){
                record.setInsertComprise(String.valueOf(coin.getAmount()));
            } else {
                record.setInsertComprise(record.getInsertComprise() + "," + coin.getAmount());
            }
            int result = recordMapper.update(record, new EmptyWrapper<>());
            if(result == 0){
                log.error("投币,更新record的字段结果:{}, record信息:{}", result, record);
            }
        } else {
            record = new Record();
            record.setInsertAmount(coin.getAmount());
            record.setInsertComprise(String.valueOf(coin.getAmount()));
            record.setRecordStatus(0);
            int result = recordMapper.insert(record);
            if(result == 0 ){
                log.error("投币, 插入record的字段结果:{}, record信息:{}", result, record);
            }
        }
    }
}
