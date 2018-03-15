package io.transwarp.tdc.gn.common.exception;

/**
 * 18-2-5 created by zado
 */
public enum ErrorCode {
    METHOD_NOT_SUPPORTED(100001),
    PAYLOAD_DESERIALIZE_ERROR(100002),
    PAYLOAD_SERIALIZE_ERROR(100003),
    INVALID_TOPIC_ERROR(200001),
    INVALID_PARTITION_ERROR(200002),
    SEND_ERROR(200003),


    TOPIC_NOT_FOUND(300001),
    TOPIC_NOT_SUBSCRIBED(300002),
    OFFSET_COMMIT_ERROR(300003),
    SERDE_REGISTER_ERROR(300004),
    SERDE_PARSE_ERROR(300005),
    META_INFO_ERROR(300006)
    ;

    private final int code;

    ErrorCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
