package io.transwarp.tdc.gn.client.recipes;

import io.transwarp.tdc.gn.client.ConsumeShutdownStrategy;

import java.util.concurrent.atomic.AtomicBoolean;

public class DefaultShutdownStrategy implements ConsumeShutdownStrategy {
    private AtomicBoolean shutdown = new AtomicBoolean(false);

    @Override
    public boolean isShutdown() {
        return shutdown.get();
    }

    @Override
    public void shutdown() {
        shutdown.set(true);
    }
}
