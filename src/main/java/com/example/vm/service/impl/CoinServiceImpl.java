package com.example.vm.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.vm.dao.mapper.CoinMapper;
import com.example.vm.model.entity.Coin;
import com.example.vm.model.entity.Record;
import com.example.vm.service.CoinService;
import com.example.vm.service.RecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 硬币服务实现
 */
@Slf4j
@Service
public class CoinServiceImpl implements CoinService {

    @Resource
    private CoinMapper coinMapper;

    @Resource
    private RecordService recordService;

    @Override
    public List<Coin> giveCoins(Record record) {
        log.info("需要找零的交易记录数据:{}", record);
        Integer amount = record.getInsertAmount() - record.getAmount();
        // 如果不需要找零, 返回一个空的list
        if (amount == 0) return new ArrayList<>();
        if (amount > 0) {// 查询单一面值低于当前找零金额的所有种类硬币
            QueryWrapper<Coin> wrapper = new QueryWrapper<>();
            log.info("amount-----" + amount);
            wrapper.le("amount", amount);
            wrapper.orderByDesc("amount");
            List<Coin> coins = coinMapper.selectList(wrapper);
            log.info("coins---------" + coins);
            // 计算找零的硬币组合
            return getGiveCoins(coins, amount);
        }

        return null;
    }

    /**
     * 投入硬币逻辑:
     * 有当前币种, 数量+1
     * 没有当前币种, 保存, 数量为1
     * 增加一条交易记录
     *
     * @param coin
     */
    @Override
    public void saveCoin(Coin coin) {
        QueryWrapper<Coin> wrapper = new QueryWrapper<>();
        wrapper.eq("amount", coin.getAmount());
        Coin dbCoin = coinMapper.selectOne(wrapper);
        // 没有此币种
        if (dbCoin == null) {
            int result = coinMapper.insert(coin);
            log.info("币种:{}, 保存结果:{}", coin, result);
        } else {
            // amount乐观锁更新
            QueryWrapper<Coin> updateWrapper = new QueryWrapper<>();
            updateWrapper.eq("amount", dbCoin.getAmount());
            dbCoin.setCount(dbCoin.getCount() + 1);
            int result = coinMapper.update(dbCoin, updateWrapper);
            if (result == 0) {
                QueryWrapper<Coin> secondWrapper = new QueryWrapper<>();
                wrapper.eq("amount", coin.getAmount());
                Coin SecondDbCoin = coinMapper.selectOne(secondWrapper);
                QueryWrapper<Coin> secondUpdateWrapper = new QueryWrapper<>();
                secondUpdateWrapper.eq("amount", SecondDbCoin.getAmount());
                SecondDbCoin.setCount(SecondDbCoin.getCount() + 1);
                result = coinMapper.update(SecondDbCoin, secondUpdateWrapper);
                if (result == 0) {
                    log.error("Coin未更新,count值一直更新, coin信息:{}", SecondDbCoin);
                }
            }
        }
        recordService.updateRecordInfo(coin);
    }

    public static void main(String[] args) {
        // SQL中有排序
//        System.out.println(65 + " ==== " + getGiveCoins(coins, 65));
        for (int i = 0; i < 20; i++) {
            List<Coin> coins = new ArrayList<>();
            coins.add(new Coin(5l, 100, 10));
            coins.add(new Coin(4l, 25, 10));
            coins.add(new Coin(3l, 10, 5));
            System.out.println(i * 5 + " ==== " + getGiveCoins(coins, i * 5));
        }
    }

    /**
     * 计算找零的硬币组合,
     * 仅限于人民币算法. 例如, 1,2,5,10,20,50,100 等面值.
     * 非人民币面值不支持, 此题中的25支持.
     *
     * @param coins
     * @param target
     * @return
     */
    private static List<Coin> getGiveCoins(List<Coin> coins, Integer target) {
        if (coins == null || coins.size() == 0) return new ArrayList<>();

        List<Coin> result = new ArrayList<>();
        int temp = target;
        int c = 0;
        for (int i = 0; i < coins.size(); i++) {
            Coin coin = coins.get(i);
            // 需要几个当前面值的硬币
            int count = temp / coin.getAmount();
            if (count == 0) continue;
            count = Math.min(count, coin.getCount());
            temp -= coin.getAmount() * count;
            result.add(Coin.builder().id(coin.getId()).amount(coin.getAmount()).count(count).build());
            if (coin.getAmount() == 25) {
                c = count;
            }
        }
        if (temp > 0 && c > 0) {
            // 修改25的数量减一
            coins.get(0).setCount(c - 1);
            return getGiveCoins(coins, target);
        } else if (temp > 0 && c == 0) {
            return null;
        } else {
            return result;
        }
    }

}
