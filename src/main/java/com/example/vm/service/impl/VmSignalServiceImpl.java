package com.example.vm.service.impl;

import com.example.vm.common.ConstsCode;
import com.example.vm.event.VmSignalEvent;
import com.example.vm.model.entity.Coin;
import com.example.vm.model.entity.Product;
import com.example.vm.model.entity.Record;
import com.example.vm.service.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.List;

/**
 * 信号服务实现
 */
@Slf4j
@Service
public class VmSignalServiceImpl implements VmSignalService {


    @Resource
    private ProductService productService;

    @Resource
    private RecordService recordService;

    @Resource
    private NoticeDeviceService noticeDeviceService;

    @Resource
    private CoinService coinService;

    /**
     * 处理信号
     * @param event 信号事件对象
     */
    @Override
    public void handleSignal(VmSignalEvent event) {
        Assert.notNull(event, "input signal is null, please check hardware");
        Assert.notNull(event.getSignal(), "input signal is null, please check hardware");
        Signal signal = initSignal(event);

        if(SignalType.INSERT.equals(signal.getSignalType())){
            handleInsertSignal(signal);
        }
        if(SignalType.OPERATION.equals(signal.getSignalType())){
            handleOperationSignal(signal);
        }
        if(SignalType.POSITION.equals(signal.getSignalType())){
            handlePositionSignal(signal);
        }

    }

    /**
     * 处理位置类型的信号
     * @param signal
     */
    private void handlePositionSignal(Signal signal) {
        log.info("处理位置信号");
        Product product = new Product();
        product.setId(Long.valueOf(signal.getSignalValue()));
        productService.chooseProduct(product);
    }

    /**
     * 处理操作类型的信号
     * @param signal
     */
    private void handleOperationSignal(Signal signal) {
        log.info("处理操作信号");
        if(signal.signalValue.equals(ConstsCode.OPERATION_CONFIRM)){
            operationConfirm();
        } else if(signal.signalValue.equals(ConstsCode.OPERATION_CANCEL)){
            operationCancel();
        }

    }

    /**
     * 取消操作信号处理
     */
    private void operationCancel() {
        // 修改交易记录状态未取消状态
        // 吐出, 投入的金币
        // 投入的金币组成, 在record中有记录
    }

    /**
     * 确认操作信号处理
     */
    private void operationConfirm() {
        // 确认投币金额和商品价格,
        Record record = recordService.confirmAmountAndInsert();
        if(record == null){// 投入的金币小于商品价格
            noticeDeviceService.noticeDeviceInsert();
            return ;
        }

        // 计算最优找零算法
        List<Coin> coins = coinService.giveCoins(record);
        if(coins == null) {
            noticeDeviceService.noticeDeviceCoinNotEnough();
            return;
        }

        // 确认库存, 减库存, 提示库存不足
        Boolean result = productService.confirmProductCount(record);
        if(!result){
            noticeDeviceService.noticeDeviceInventoryDeficiency();
            // TODO 吐硬币
            return ;
        } else {
            // 减库存
            productService.subProductCount(record);
        }
        // 通知设备,吐商品
        noticeDeviceService.noticeDeviceGiveProduct(record);
        // 通知设备,找零
        noticeDeviceService.noticeDeviceGiveCoins(coins);
        // 更新交易记录,完成交易
        recordService.completeRecord(record, coins);

    }

    /**
     * 处理投入类型的信号
     * @param signal
     */
    private void handleInsertSignal(Signal signal) {
        log.info("处理投入信号");
        Coin coin = new Coin();
        coin.setCount(1);
        if(signal.signalValue.equals(ConstsCode.INSERT_COINS_100)){
            coin.setAmount(1 * 100);
        } else if(signal.signalValue.equals(ConstsCode.INSERT_COINS_10)){
            coin.setAmount(10);
        } else if(signal.signalValue.equals(ConstsCode.INSERT_COINS_25)){
            coin.setAmount(25);
        } else {
            coin.setAmount(signal.signalValue);
        }
        log.info("coin------" + coin);
        coinService.saveCoin(coin);
    }


    /**
     * 检查信号类型
     * @return
     */
    private SignalType initSignalType(Integer signal) {
        if((String.valueOf(signal)).startsWith("1")){
            return SignalType.INSERT;
        }
        if((String.valueOf(signal)).startsWith("2")){
            return SignalType.POSITION;
        }
        if((String.valueOf(signal)).startsWith("3")){
            return SignalType.OPERATION;
        }
        return SignalType.INVALID;
    }

    /**
     * 初始化Signal实体
     * @param event
     * @return
     */
    private Signal initSignal(VmSignalEvent event){
        SignalType type = initSignalType(event.getSignal());
        return new Signal(type, event.getSignal());
    }

    /**
     * 封装信号
     */
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    class Signal{
        private SignalType signalType;
        private Integer signalValue;
    }

    /**
     * 信号类型:操作,投入,位置
     */
    enum SignalType{

        OPERATION("OPERATION"),
        INSERT("INSERT"),
        POSITION("POSITION"),
        INVALID("INVALID");

        private String value;

        private SignalType(String value){
            this.value = value;
        }

        public String getValue() {
            return value;
        }

    }
}
