package io.ts.tdc.gn.dlock;

import io.ts.tdc.dlock.annotation.EnableDLock;
import io.ts.tdc.dlock.service.DLockTypeProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

/**
 * 18-2-8 created by zado
 */
@Configuration
@EnableDLock
public class GNLockConfiguration {

    // 注册所有的锁类型
    @Bean
    public DLockTypeProvider dLockTypeProvider() {
        return () -> Arrays.asList(GNLockType.values());
    }
}
