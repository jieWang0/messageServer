package io.transwarp.tdc.notif.client.recipes;

import io.transwarp.tdc.notif.common.NConsumerRecord;

public interface Persist<T> {

    void persist(NConsumerRecord<T> record);

}
