package io.transwarp.tdc.gn.client.consume;

import io.transwarp.tdc.tracing.retrofit.RetrofitArgs;

import java.util.LinkedHashMap;
import java.util.Map;

public class ConsumerArgs {
    private final String topic;
    private final String group;
    private final String user;
    private final int pollTimeoutMillis;
    private final int pollBatchSize;
    private final boolean autoCommitEnabled;
    private final int autoCommitIntervalMillis;
    private final RetrofitArgs retrofitArgs;
    private Map<String, Object> additionalArgs;

    public ConsumerArgs(String topic, String group, String user, int pollTimeoutMillis,
                        int pollBatchSize, boolean autoCommitEnabled, int autoCommitIntervalMillis,
                        RetrofitArgs retrofitArgs, Map<String, Object> additionalArgs) {
        this.topic = topic;
        this.group = group;
        this.user = user;
        this.pollTimeoutMillis = pollTimeoutMillis;
        this.pollBatchSize = pollBatchSize;
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

    public String getUser() {
        return user;
    }

    public int getPollTimeoutMillis() {
        return pollTimeoutMillis;
    }

    public int getPollBatchSize() {
        return pollBatchSize;
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
        private String user;
        private int pollTimeoutMillis = 5000;
        private int pollBatchSize = 50;
        private boolean autoCommitEnabled = false;
        private int autoCommitIntervalMillis = 5000;
        private RetrofitArgs retrofitArgs;
        private Map<String, Object> additionalArgs;

        public Builder() {
        }

        public Builder topic(String topic) {
            this.topic = topic;
            return this;
        }
        public Builder group(String group) {
            this.group = group;
            return this;
        }
        public Builder user(String user) {
            this.user = user;
            return this;
        }
        public Builder pollTimeoutMillis(int pollTimeoutMillis) {
            this.pollTimeoutMillis = pollTimeoutMillis;
            return this;
        }
        public Builder pollBatchSize(int pollBatchSize) {
            this.pollBatchSize = pollBatchSize;
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
        public Builder additionalArg(String key, Object value) {
            if (this.additionalArgs == null) {
                this.additionalArgs = new LinkedHashMap<>();
            }
            this.additionalArgs.put(key, value);
            return this;
        }

        public ConsumerArgs build() {
            return new ConsumerArgs(topic, group, user, pollTimeoutMillis, pollBatchSize, autoCommitEnabled,
                    autoCommitIntervalMillis, retrofitArgs, additionalArgs);
        }
    }
}
