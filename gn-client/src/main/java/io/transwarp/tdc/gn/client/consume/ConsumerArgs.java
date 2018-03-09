package io.transwarp.tdc.gn.client.consume;

import io.transwarp.tdc.tracing.retrofit.RetrofitArgs;

import java.util.LinkedHashMap;
import java.util.Map;

public class ConsumerArgs {
    private final String topic;
    private final String group;
    private final int pollTimeoutMillis;
    private final boolean autoCommitEnabled;
    private final int autoCommitIntervalMillis;
    private final RetrofitArgs retrofitArgs;
    private Map<String, Object> additionalArgs;

    public ConsumerArgs(String topic, String group, int pollTimeoutMillis,
                        boolean autoCommitEnabled, int autoCommitIntervalMillis,
                        RetrofitArgs retrofitArgs, Map<String, Object> additionalArgs) {
        this.topic = topic;
        this.group = group;
        this.pollTimeoutMillis = pollTimeoutMillis;
        this.autoCommitEnabled = autoCommitEnabled;
        this.autoCommitIntervalMillis = autoCommitIntervalMillis;
        this.retrofitArgs = retrofitArgs;
        this.additionalArgs = additionalArgs;
    }

    public String getTopic() {
        return topic;
    }

    public String getGroup() {
        return group;
    }

    public int getPollTimeoutMillis() {
        return pollTimeoutMillis;
    }

    public boolean isAutoCommitEnabled() {
        return autoCommitEnabled;
    }

    public int getAutoCommitIntervalMillis() {
        return autoCommitIntervalMillis;
    }

    public RetrofitArgs getRetrofitArgs() {
        return retrofitArgs;
    }

    public Map<String, Object> getAdditionalArgs() {
        return additionalArgs;
    }

    public static class Builder {
        private String topic;
        private String group;
        private int pollTimeoutMillis = 5000;
        private boolean autoCommitEnabled = false;
        private int autoCommitIntervalMillis = 5000;
        private RetrofitArgs retrofitArgs;
        private Map<String, Object> additionalArgs;

        public Builder() {
        }

        public Builder(ConsumerArgs consumerArgs) {
        }

        public Builder serverLocation() {
            return this;
        }
        public Builder topic(String topic) {
            this.topic = topic;
            return this;
        }
        public Builder group(String group) {
            this.group = group;
            return this;
        }
        public Builder pollTimeoutMillis(int pollTimeoutMillis) {
            this.pollTimeoutMillis = pollTimeoutMillis;
            return this;
        }
        public Builder autoCommitEnabled(boolean autoCommitEnabled) {
            this.autoCommitEnabled = autoCommitEnabled;
            return this;
        }
        public Builder autoCommitIntervalMillis(int autoCommitIntervalMillis) {
            this.autoCommitIntervalMillis = autoCommitIntervalMillis;
            return this;
        }
        public Builder retrofitArgs(RetrofitArgs retrofitArgs) {
            this.retrofitArgs = retrofitArgs;
            return this;
        }
        public Builder additionalArgs(Map<String, Object> additionalArgs) {
            this.additionalArgs = new LinkedHashMap<>(additionalArgs);
            return this;
        }

        public ConsumerArgs build() {
            return new ConsumerArgs(topic, group, pollTimeoutMillis, autoCommitEnabled,
                    autoCommitIntervalMillis, retrofitArgs, additionalArgs);
        }
    }
}
