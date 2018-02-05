package io.transwarp.tdc.gn.service.condition;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * @see ConditionConstants
 * 根据设置的GN_SERVICE_IMPL值来判断是否使用kafka的实现
 * 使用方法@Conditional(KafkaImpl.class) 在bean或者class上
 *
 * 18-1-30 created by zado
 */
public class KafkaImpl implements Condition {

    @Override
    public boolean matches(ConditionContext conditionContext, AnnotatedTypeMetadata annotatedTypeMetadata) {
        Environment env = conditionContext.getEnvironment();

        return env != null
            && env.getProperty(ConditionConstants.GN_SERVICE_IMPL) != null
            && env.getProperty(ConditionConstants.GN_SERVICE_IMPL).equals(ConditionConstants.KAFKA_BACKEND_IMPL);
    }
}
