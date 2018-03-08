package io.transwarp.tdc.gn.server.converter;

import io.transwarp.mangix.web.converter.AbstractConverter;
import io.transwarp.tdc.gn.common.transport.TRecord;
import io.transwarp.tdc.gn.model.Record;

/**
 * 18-2-8 created by zado
 */
public class RecordConverter extends AbstractConverter<Record, TRecord> {
    @Override
    public TRecord convert(Record record) {
        if (record == null) {
            return nilRecord();
        } else {
            TRecord result = new TRecord();

            result.setNil(false);
            result.setOffset(record.getOffset());
            result.setTopic(record.getTopic());
            result.setPayload(record.getPayload());
            result.setCreateTime(record.getCreateTime());

            return result;
        }
    }

    private TRecord nilRecord() {
        TRecord result = new TRecord();
        result.setNil(true);

        return result;
    }
}
