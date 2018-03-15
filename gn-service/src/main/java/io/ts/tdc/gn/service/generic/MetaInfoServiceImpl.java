package io.ts.tdc.gn.service.generic;

import io.ts.tdc.gn.common.exception.ErrorCode;
import io.ts.tdc.gn.common.exception.GNException;
import io.ts.tdc.gn.model.MetaInfo;
import io.ts.tdc.gn.service.MetaInfoService;
import io.ts.tdc.gn.service.condition.ConditionConstants;
import io.ts.tdc.gn.common.exception.ErrorCode;
import io.ts.tdc.gn.common.exception.GNException;
import io.ts.tdc.gn.model.MetaInfo;
import io.ts.tdc.gn.service.MetaInfoService;
import io.ts.tdc.gn.service.condition.ConditionConstants;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 18-2-8 created by zado
 */
@Service
public class MetaInfoServiceImpl implements MetaInfoService {

    @Resource
    private Environment env;

    @Override
    public MetaInfo getMetaInfo() {
        String type = env.getProperty(ConditionConstants.GN_SERVICE_IMPL);
        String url;
        if (ConditionConstants.DB_BACKEND_IMPL.equals(type)) {
            url = env.getProperty("gn.public.url");
        } else if (ConditionConstants.KAFKA_BACKEND_IMPL.equals(type)) {
            // todo set url to kafka server address
            url = "kafka.bootstrap.server";
        } else {
            throw new GNException(ErrorCode.METHOD_NOT_SUPPORTED,
                String.format("Generic Notification server not implements via [%s]", type));
        }

        MetaInfo metaInfo = new MetaInfo();
        metaInfo.setType(type);
        metaInfo.setUrl(url);
        return metaInfo;
    }
}
