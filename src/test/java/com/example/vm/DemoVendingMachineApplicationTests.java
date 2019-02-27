package com.example.vm;

import com.example.vm.event.VmSignalEvent;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoVendingMachineApplicationTests {

    @Autowired
    private ApplicationContext applicationContext;

    /**
     * 10美分,25美分, 买巧克力
     */
    @Test
    public void test1(){
        // 选择商品位置2的商品
        applicationContext.publishEvent(new VmSignalEvent(2002, 2002));
        // 投币10美分
        applicationContext.publishEvent(new VmSignalEvent(1001, 1001));
        // 投币25美分
        applicationContext.publishEvent(new VmSignalEvent(1002, 1002));
        // 确认操作
        applicationContext.publishEvent(new VmSignalEvent(3001, 3001));
    }

    /**
     * 1美元买腰果
     */
    @Test
    public void test3(){
        // 选择商品位置2的商品
        applicationContext.publishEvent(new VmSignalEvent(2003, 2003));
        // 投币1美元
        applicationContext.publishEvent(new VmSignalEvent(1003, 1003));
        // 确认操作
        applicationContext.publishEvent(new VmSignalEvent(3001, 3001));

    }

    /**
     * 1美元买巧克力
     */
    @Test
    public void test2(){
        // 投币100美分
        applicationContext.publishEvent(new VmSignalEvent(1003, 1003));
        // 选择商品位置2的商品, 35美分
        applicationContext.publishEvent(new VmSignalEvent(2002, 2002));
        // 确认操作
        applicationContext.publishEvent(new VmSignalEvent(3001, 3001));
    }

    /**
     * 1美元买巧克力
     */
    @Test
    public void test4(){
        // 投币100美分
        applicationContext.publishEvent(new VmSignalEvent(1003, 1003));
        // 选择商品位置2的商品, 35美分
        applicationContext.publishEvent(new VmSignalEvent(2002, 2002));
        // 取消操作
        applicationContext.publishEvent(new VmSignalEvent(3002, 3002));
    }


}
