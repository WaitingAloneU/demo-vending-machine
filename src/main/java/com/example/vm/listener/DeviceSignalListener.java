package com.example.vm.listener;

import com.example.vm.event.VmSignalEvent;
import com.example.vm.service.VmSignalService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 监听设备信号
 */
@Slf4j
@Component
public class DeviceSignalListener implements ApplicationListener<VmSignalEvent> {

    @Resource
    private VmSignalService vmSignalService;

    /**
     * 监听到信号以后, 调用服务,处理信号
     * @param event
     */
    @Override
    public void onApplicationEvent(VmSignalEvent event) {
        log.debug("接收到信号:" + event.getSignal());
        vmSignalService.handleSignal(event);
    }
}
