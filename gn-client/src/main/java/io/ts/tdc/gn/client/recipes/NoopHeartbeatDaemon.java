package io.ts.tdc.gn.client.recipes;

import io.ts.tdc.gn.client.ConsumeHeartbeatDaemon;

/**
 * no operation
 */
public class NoopHeartbeatDaemon implements ConsumeHeartbeatDaemon {
    @Override
    public void setInterval(int intervalMillis) {

    }

    @Override
    public void start() {

    }

    @Override
    public void close() {

    }
}
