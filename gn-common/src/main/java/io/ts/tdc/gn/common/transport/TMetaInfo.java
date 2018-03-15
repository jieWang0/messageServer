package io.ts.tdc.gn.common.transport;

/**
 * 18-2-5 created by zado
 *
 * 服务和客户端通信使用的数据格式
 *
 * need to add more server options that may affect consumer side
 *
 */
public class TMetaInfo {

    /**
     * generic notification服务实现方式
     * 比如database, kafka
     */
    private String type;

    /**
     * generic notification的服务地址
     * 比如web服务地址, kafka server地址
     */
    private String url;

    public TMetaInfo() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "TMetaInfo{" +
            "type='" + type + '\'' +
            ", url='" + url + '\'' +
            '}';
    }
}
