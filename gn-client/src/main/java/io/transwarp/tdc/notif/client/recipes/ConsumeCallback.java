package io.transwarp.tdc.notif.client.recipes;

import io.transwarp.tdc.notif.common.NConsumerRecord;

public interface ConsumeCallback<T> {

    void onConsuming(NConsumerRecord<T> data);
}
