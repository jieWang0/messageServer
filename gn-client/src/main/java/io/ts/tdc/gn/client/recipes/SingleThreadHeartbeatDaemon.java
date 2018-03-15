package io.ts.tdc.gn.client.recipes;

import io.ts.tdc.gn.client.ConsumeHeartbeatDaemon;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * single thread for heartbeat
 */
public class SingleThreadHeartbeatDaemon implements ConsumeHeartbeatDaemon {
    private static final Logger logger = LoggerFactory.getLogger(SingleThreadHeartbeatDaemon.class);
    private static final AtomicInteger heartbeatThreadCounter = new AtomicInteger(0);

    private int intervalMillis;
    private Thread heartbeatThread;
    private Runnable heartbeatAction;

    public SingleThreadHeartbeatDaemon(Runnable heartbeatAction) {
        this.heartbeatAction = heartbeatAction;
    }

    @Override
    public void setInterval(int intervalMillis) {
        this.intervalMillis = intervalMillis;
    }

    @Override
    public void start() {
        heartbeatThread = new Thread(() -> {
            try {
                Thread.sleep(intervalMillis);
                while (true) {
                    logger.trace("Heartbeat on {}", Thread.currentThread().getName());
                    heartbeatAction.run();
                    Thread.sleep(intervalMillis);
                }
            } catch (InterruptedException ie) {
                /* safely ignored */
            }
        });
        heartbeatThread.setName("GN-Consumer-Heartbeat-" + heartbeatThreadCounter.incrementAndGet());
        heartbeatThread.start();
    }

    @Override
    public void close() {
        if (heartbeatThread != null && heartbeatThread.getState() != Thread.State.TERMINATED
                && heartbeatThread.getState() != Thread.State.NEW) {
            heartbeatThread.interrupt();
            heartbeatThread = null;
        }
    }
}
