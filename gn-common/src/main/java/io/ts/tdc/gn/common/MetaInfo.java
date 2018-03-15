package io.ts.tdc.gn.common;

import java.util.Map;

public class MetaInfo {
    private BackendType backendType;
    private String serverUrl;
    private Map<String, String> clientProperties;
    private Map<String, String> serverProperties;

    /**
     * backend type: KAFKA, DATABASE
     * @return
     */
    public BackendType getBackendType() {
        return backendType;
    }

    public void setBackendType(BackendType backendType) {
        this.backendType = backendType;
    }

    /**
     * server url
     * @return
     */
    public String getServerUrl() {
        return serverUrl;
    }

    public void setServerUrl(String serverUrl) {
        this.serverUrl = serverUrl;
    }

    /**
     * client properties that should be set
     * @return
     */
    public Map<String, String> getClientProperties() {
        return clientProperties;
    }

    public void setClientProperties(Map<String, String> clientProperties) {
        this.clientProperties = clientProperties;
    }

    /**
     * server properties
     * @return
     */
    public Map<String, String> getServerProperties() {
        return serverProperties;
    }

    public void setServerProperties(Map<String, String> serverProperties) {
        this.serverProperties = serverProperties;
    }
}
