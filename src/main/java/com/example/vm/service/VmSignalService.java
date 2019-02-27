package com.example.vm.service;

import com.example.vm.event.VmSignalEvent;

public interface VmSignalService {

    /**
     * 处理设备信号
     * @param event
     */
    public void handleSignal(VmSignalEvent event);
}
