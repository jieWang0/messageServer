package io.transwarp.tdc.gn.server.converter;

import io.transwarp.mangix.web.converter.AbstractConverter;
import io.transwarp.tdc.gn.common.transport.TRecords;
import io.transwarp.tdc.gn.model.Records;

import java.util.stream.Collectors;

public class RecordsConverter extends AbstractConverter<Records, TRecords> {
    private RecordConverter recordConv = new RecordConverter();

    @Override
    public TRecords convert(Records records) {
        TRecords result = new TRecords();
        result.setToConsume(records.isToConsume());
        result.setLocked(records.isLocked());
        result.setData(records.getData() == null ? null :
                records.getData().stream().map(recordConv::convert).collect(Collectors.toList()));
        return result;
    }
}
