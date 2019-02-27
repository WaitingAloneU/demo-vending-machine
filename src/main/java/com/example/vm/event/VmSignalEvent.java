package com.example.vm.event;

import lombok.Data;
import org.springframework.context.ApplicationEvent;

/**
 * 信号spring事件,封装自贩售机的发送出来的信号
 */
@Data
public class VmSignalEvent extends ApplicationEvent {

    private Integer signal;
    /**
     * Create a new ApplicationEvent.
     *
     * @param source the object on which the event initially occurred (never {@code null})
     */
    public VmSignalEvent(Object source, Integer signal) {
        super(source);
        this.signal = signal;
    }
}
