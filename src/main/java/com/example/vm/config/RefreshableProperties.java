package com.example.vm.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 可以动态刷新的配置
 */
@Data
@ConfigurationProperties(prefix = "test")
public class RefreshableProperties {

    private int limit;

}
